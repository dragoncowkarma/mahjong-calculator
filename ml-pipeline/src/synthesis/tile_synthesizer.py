import os
import random
import cv2
import albumentations as A

# Map class names to IDs
CLASS_MAPPING = {
    "Man1": 0, "Man2": 1, "Man3": 2, "Man4": 3, "Man5": 4, "Man6": 5, "Man7": 6, "Man8": 7, "Man9": 8,
    "Man5-Dora": 4,  # treat as 5
    "Pin1": 9, "Pin2": 10, "Pin3": 11, "Pin4": 12, "Pin5": 13, "Pin6": 14, "Pin7": 15, "Pin8": 16, "Pin9": 17,
    "Pin5-Dora": 13,  # treat as 5
    "Sou1": 18, "Sou2": 19, "Sou3": 20, "Sou4": 21, "Sou5": 22, "Sou6": 23, "Sou7": 24, "Sou8": 25, "Sou9": 26,
    "Sou5-Dora": 22,  # treat as 5
    "Ton": 27, "Nan": 28, "Shaa": 29, "Pei": 30, "Haku": 31, "Hatsu": 32, "Chun": 33
}

def load_tiles(raw_tile_dir):
    """Load transparent PNGs of tiles."""
    tiles = {}
    if not os.path.exists(raw_tile_dir):
        return tiles
    for filename in os.listdir(raw_tile_dir):
        if not filename.endswith(".png"):
            continue
        name = filename.replace(".png", "")
        if name in CLASS_MAPPING:
            path = os.path.join(raw_tile_dir, filename)
            # Read with alpha channel
            tile_img = cv2.imread(path, cv2.IMREAD_UNCHANGED)
            if tile_img is not None:
                tiles[name] = tile_img
    return tiles

def load_backgrounds(bg_dir):
    """Load background images."""
    bgs = []
    if not os.path.exists(bg_dir):
        return bgs
    for filename in os.listdir(bg_dir):
        if filename.endswith((".jpg", ".png", ".jpeg")):
            path = os.path.join(bg_dir, filename)
            bg_img = cv2.imread(path)
            if bg_img is not None:
                bgs.append(bg_img)
    return bgs

def overlay_image(bg, fg, x, y):
    """Overlay an RGBA foreground image onto an RGB background image at (x, y)."""
    h, w = fg.shape[:2]
    bg_h, bg_w = bg.shape[:2]

    # Check bounds
    if x + w > bg_w or y + h > bg_h or x < 0 or y < 0:
        return bg

    if fg.shape[2] == 4:
        alpha = fg[:, :, 3] / 255.0
        for c in range(3):
            bg[y:y + h, x:x + w, c] = (alpha * fg[:, :, c] + (1 - alpha) * bg[y:y + h, x:x + w, c])
    else:
        bg[y:y + h, x:x + w] = fg

    return bg

def get_base_dir():
    """Get the ml-pipeline directory to resolve paths."""
    # This script is at ml-pipeline/src/synthesis/tile_synthesizer.py
    # Returns ml-pipeline/
    return os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

def generate_synthetic_data(raw_tile_dir, bg_dir, output_dir, count=100):
    """
    Generate synthetic Mahjong tile images with bounding boxes.
    """
    print(f"Generating {count} synthetic images...")

    img_out_dir = os.path.join(output_dir, "images")
    lbl_out_dir = os.path.join(output_dir, "labels")
    os.makedirs(img_out_dir, exist_ok=True)
    os.makedirs(lbl_out_dir, exist_ok=True)

    tiles = load_tiles(raw_tile_dir)
    backgrounds = load_backgrounds(bg_dir)

    if not tiles:
        print(f"No valid tiles found in {raw_tile_dir}!")
        return
    if not backgrounds:
        print(f"No backgrounds found in {bg_dir}!")
        return

    # Albumentations transform with Perspective, Blur, Noise, etc.
    transform = A.Compose([
        A.Perspective(scale=(0.05, 0.1), p=0.5),
        A.GaussianBlur(blur_limit=(3, 7), p=0.5),
        A.GaussNoise(std_range=(0.05, 0.2), p=0.5),
        A.RandomBrightnessContrast(p=0.5)
    ], bbox_params=A.BboxParams(format='yolo', min_visibility=0.1, label_fields=['class_labels']))

    tile_names = list(tiles.keys())

    generated = 0
    attempts = 0
    max_attempts = count * 10

    while generated < count and attempts < max_attempts:
        attempts += 1
        bg = random.choice(backgrounds).copy()
        bg_h, bg_w = bg.shape[:2]

        num_tiles = random.randint(1, 5)
        bboxes = []
        labels = []

        # Place tiles without overlap check (for simplicity, they might overlap)
        for _ in range(num_tiles):
            t_name = random.choice(tile_names)
            t_img = tiles[t_name]

            # Resize tile randomly
            scale = random.uniform(0.5, 1.5)
            new_w = int(t_img.shape[1] * scale)
            new_h = int(t_img.shape[0] * scale)
            if new_w <= 0 or new_h <= 0:
                continue

            t_img_resized = cv2.resize(t_img, (new_w, new_h))

            # Random position
            max_x = bg_w - new_w
            max_y = bg_h - new_h
            if max_x <= 0 or max_y <= 0:
                continue

            x = random.randint(0, max_x)
            y = random.randint(0, max_y)

            bg = overlay_image(bg, t_img_resized, x, y)

            # YOLO format: center_x, center_y, width, height (normalized)
            cx = (x + new_w / 2.0) / bg_w
            cy = (y + new_h / 2.0) / bg_h
            nw = new_w / bg_w
            nh = new_h / bg_h

            bboxes.append([cx, cy, nw, nh])
            labels.append(CLASS_MAPPING[t_name])

        if not bboxes:
            continue

        try:
            # Convert bg from BGR to RGB for albumentations
            bg_rgb = cv2.cvtColor(bg, cv2.COLOR_BGR2RGB)
            transformed = transform(image=bg_rgb, bboxes=bboxes, class_labels=labels)

            final_img = cv2.cvtColor(transformed['image'], cv2.COLOR_RGB2BGR)
            final_bboxes = transformed['bboxes']
            final_labels = transformed['class_labels']

            # Albumentations might remove bounding boxes if they fall outside the image
            # Only save if we still have at least one valid bounding box
            if not final_bboxes:
                continue

            img_filename = f"synth_{generated:04d}.jpg"
            lbl_filename = f"synth_{generated:04d}.txt"

            cv2.imwrite(os.path.join(img_out_dir, img_filename), final_img)

            with open(os.path.join(lbl_out_dir, lbl_filename), "w") as f:
                for bbox, label in zip(final_bboxes, final_labels):
                    f.write(f"{label} {bbox[0]:.6f} {bbox[1]:.6f} {bbox[2]:.6f} {bbox[3]:.6f}\n")

            generated += 1

        except Exception as e:
            print(f"Error applying transform: {e}")

    if generated < count:
        print(f"Warning: Only generated {generated}/{count} images after {max_attempts} attempts.")
    else:
        print(f"Successfully generated {generated} images.")

if __name__ == "__main__":
    base_dir = get_base_dir()
    project_root = os.path.dirname(base_dir)
    generate_synthetic_data(
        os.path.join(project_root, "mahjong-tiles-image"),
        os.path.join(base_dir, "data", "raw", "backgrounds"),
        os.path.join(base_dir, "data", "synthetic"),
        count=100
    )

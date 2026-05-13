import argparse
import os
import random
import multiprocessing
import gc
import traceback
import glob

import albumentations as A
import cv2
import numpy as np

# Mapping standard Mahjong tile names to YOLO class IDs (0-33)
CLASS_MAPPING = {
    "Man1": 0, "Man2": 1, "Man3": 2, "Man4": 3, "Man5": 4, "Man6": 5, "Man7": 6, "Man8": 7, "Man9": 8,
    "Pin1": 9, "Pin2": 10, "Pin3": 11, "Pin4": 12, "Pin5": 13, "Pin6": 14, "Pin7": 15, "Pin8": 16, "Pin9": 17,
    "Sou1": 18, "Sou2": 19, "Sou3": 20, "Sou4": 21, "Sou5": 22, "Sou6": 23, "Sou7": 24, "Sou8": 25, "Sou9": 26,
    "Ton": 27, "Nan": 28, "Shaa": 29, "Pei": 30,  # East, South, West, North (Sha is Shaa.png)
    "Haku": 31, "Hatsu": 32, "Chun": 33           # White, Green, Red Dragons
}

# Red-five (aka Dora) variations mapping to base tile ID.
DORA_MAPPING = {
    "Man5-Dora": 4, "Pin5-Dora": 13, "Sou5-Dora": 22
}
CLASS_MAPPING.update(DORA_MAPPING)

# Global variables to hold loaded data for multiprocessing workers to avoid OOM
g_tiles = {}
g_backgrounds = []
g_img_out_dir = ""
g_lbl_out_dir = ""

def load_tiles(raw_tile_dir):
    """Load and optionally composite transparent tiles onto an opaque 'Front' base."""
    tiles = {}
    if not os.path.exists(raw_tile_dir):
        print(f"Directory {raw_tile_dir} not found!")
        return tiles

    front_img_path = os.path.join(raw_tile_dir, "Front.png")
    front_img = cv2.imread(front_img_path, cv2.IMREAD_UNCHANGED)
    if front_img is None:
        print("Warning: Front.png not found, tiles might be transparent!")

    for filename in os.listdir(raw_tile_dir):
        if not filename.endswith(".png"):
            continue
        name = filename.replace(".png", "")
        if name in CLASS_MAPPING:
            path = os.path.join(raw_tile_dir, filename)
            # Read with alpha channel
            tile_img = cv2.imread(path, cv2.IMREAD_UNCHANGED)
            if tile_img is not None:
                if front_img is not None and name != "Front":
                    # Create an opaque tile by overlaying tile_img onto front_img
                    opaque_tile = front_img.copy()

                    h, w = tile_img.shape[:2]
                    fh, fw = opaque_tile.shape[:2]

                    if h <= fh and w <= fw:
                        if tile_img.shape[2] == 4:
                            alpha = tile_img[:, :, 3] / 255.0
                            for c in range(3):
                                opaque_tile[:h, :w, c] = (alpha * tile_img[:, :, c] + (1 - alpha) * opaque_tile[:h, :w, c])
                            opaque_tile[:h, :w, 3] = np.maximum(tile_img[:, :, 3], opaque_tile[:h, :w, 3])
                        else:
                            opaque_tile[:h, :w] = tile_img

                    tiles[name] = opaque_tile
                else:
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

def worker_init(tiles, backgrounds, img_dir, lbl_dir):
    global g_tiles
    global g_backgrounds
    global g_img_out_dir
    global g_lbl_out_dir
    g_tiles = tiles
    g_backgrounds = backgrounds
    g_img_out_dir = img_dir
    g_lbl_out_dir = lbl_dir

def generate_single_image(args):
    idx, seed = args
    random.seed(seed)
    np.random.seed(seed)

    # Albumentations transform with Perspective, Blur, Noise, etc.
    transform = A.Compose([
        A.Perspective(scale=(0.05, 0.1), p=0.5),
        A.GaussianBlur(blur_limit=(3, 7), p=0.5),
        A.GaussNoise(std_range=(0.05, 0.2), p=0.5),
        A.HueSaturationValue(hue_shift_limit=20, sat_shift_limit=30, val_shift_limit=20, p=0.5),
        A.RandomBrightnessContrast(p=0.5)
    ], bbox_params=A.BboxParams(format='yolo', min_visibility=0.1, label_fields=['class_labels']))

    tile_names = list(g_tiles.keys())

    max_attempts = 10
    attempts = 0

    while attempts < max_attempts:
        attempts += 1
        try:
            bg = random.choice(g_backgrounds).copy()
            bg_h, bg_w = bg.shape[:2]

            num_tiles = random.randint(5, 14)
            bboxes = []
            labels = []

            # Place tiles ensuring NO overlap
            placed_boxes = []  # To track (x1, y1, x2, y2) of placed tiles

            for _ in range(num_tiles):
                t_name = random.choice(tile_names)
                t_img = g_tiles[t_name]

                # Resize tile randomly
                scale = random.uniform(0.5, 1.5)
                new_w = int(t_img.shape[1] * scale)
                new_h = int(t_img.shape[0] * scale)
                if new_w <= 0 or new_h <= 0:
                    continue

                t_img_resized = cv2.resize(t_img, (new_w, new_h))

                # Apply random rotation to the tile image
                angle = random.uniform(-180, 180)
                center = (new_w // 2, new_h // 2)
                rot_mat = cv2.getRotationMatrix2D(center, angle, 1.0)

                # Calculate new bounding box for the rotated image
                cos = np.abs(rot_mat[0, 0])
                sin = np.abs(rot_mat[0, 1])
                rot_w = int((new_h * sin) + (new_w * cos))
                rot_h = int((new_h * cos) + (new_w * sin))

                # Adjust rotation matrix to account for translation
                rot_mat[0, 2] += (rot_w / 2) - center[0]
                rot_mat[1, 2] += (rot_h / 2) - center[1]

                t_img_rotated = cv2.warpAffine(t_img_resized, rot_mat, (rot_w, rot_h), flags=cv2.INTER_LINEAR, borderMode=cv2.BORDER_CONSTANT, borderValue=(0, 0, 0, 0))

                # Random position with overlap check
                max_x = bg_w - rot_w
                max_y = bg_h - rot_h
                if max_x <= 0 or max_y <= 0:
                    continue

                max_placement_attempts = 50
                placed = False

                for _ in range(max_placement_attempts):
                    x = random.randint(0, max_x)
                    y = random.randint(0, max_y)

                    # Check for overlap
                    overlap = False
                    for bx1, by1, bx2, by2 in placed_boxes:
                        # If rectangles intersect, there is an overlap
                        if not (x + rot_w <= bx1 or x >= bx2 or y + rot_h <= by1 or y >= by2):
                            overlap = True
                            break

                    if not overlap:
                        placed = True
                        placed_boxes.append((x, y, x + rot_w, y + rot_h))
                        break

                if not placed:
                    continue # Could not find a non-overlapping spot

                # Add shadow effect under the tile
                shadow_offset_x = random.randint(5, 15)
                shadow_offset_y = random.randint(5, 15)

                shadow_x = x + shadow_offset_x
                shadow_y = y + shadow_offset_y

                if shadow_x + rot_w <= bg_w and shadow_y + rot_h <= bg_h:
                    alpha_mask = t_img_rotated[:, :, 3] / 255.0
                    shadow_alpha = alpha_mask * random.uniform(0.3, 0.5)
                    for c in range(3):
                        bg[shadow_y:shadow_y + rot_h, shadow_x:shadow_x + rot_w, c] = (shadow_alpha * 0 + (1 - shadow_alpha) * bg[shadow_y:shadow_y + rot_h, shadow_x:shadow_x + rot_w, c])

                # Overlay the actual tile, tile has 100% opaque face because of Front composite
                bg = overlay_image(bg, t_img_rotated, x, y)

                # YOLO format: center_x, center_y, width, height (normalized)
                cx = (x + rot_w / 2.0) / bg_w
                cy = (y + rot_h / 2.0) / bg_h
                nw = rot_w / bg_w
                nh = rot_h / bg_h

                bboxes.append([cx, cy, nw, nh])
                labels.append(CLASS_MAPPING[t_name])

            if not bboxes:
                continue

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

            img_filename = f"synth_{idx:04d}.jpg"
            lbl_filename = f"synth_{idx:04d}.txt"

            cv2.imwrite(os.path.join(g_img_out_dir, img_filename), final_img)

            with open(os.path.join(g_lbl_out_dir, lbl_filename), "w") as f:
                for bbox, label in zip(final_bboxes, final_labels):
                    f.write(f"{int(label)} {bbox[0]:.6f} {bbox[1]:.6f} {bbox[2]:.6f} {bbox[3]:.6f}\n")

            return idx

        except Exception:
            print(f"Error applying transform for idx {idx}:")
            traceback.print_exc()

    return -1

def generate_synthetic_data(raw_tile_dir, bg_dir, output_dir, count=100000, batch_size=1000):
    """
    Generate synthetic Mahjong tile images with bounding boxes.
    """
    img_out_dir = os.path.join(output_dir, "images")
    lbl_out_dir = os.path.join(output_dir, "labels")
    os.makedirs(img_out_dir, exist_ok=True)
    os.makedirs(lbl_out_dir, exist_ok=True)

    # Resume capability: scan existing files to determine start_idx
    existing_files = glob.glob(os.path.join(img_out_dir, "synth_*.jpg"))
    max_idx = -1
    for f in existing_files:
        try:
            basename = os.path.basename(f)
            idx = int(basename.replace("synth_", "").replace(".jpg", ""))
            if idx > max_idx:
                max_idx = idx
        except ValueError:
            pass

    start_idx = max_idx + 1
    print(f"Found existing max index: {max_idx}. Resuming generation starting at index {start_idx} for {count} images...")

    tiles = load_tiles(raw_tile_dir)
    backgrounds = load_backgrounds(bg_dir)

    if not tiles:
        print(f"No valid tiles found in {raw_tile_dir}!")
        return
    if not backgrounds:
        print(f"No backgrounds found in {bg_dir}!")
        return

    # Using Multiprocessing pool for batch processing, passing images via initializer to prevent memory blowup
    pool = multiprocessing.Pool(
        processes=multiprocessing.cpu_count(),
        initializer=worker_init,
        initargs=(tiles, backgrounds, img_out_dir, lbl_out_dir)
    )

    total_generated = 0

    # We maintain a queue of indices to generate. This allows strictly filling any gaps dynamically.
    indices_to_generate = list(range(start_idx, start_idx + count))

    while indices_to_generate:
        current_batch_indices = indices_to_generate[:batch_size]
        indices_to_generate = indices_to_generate[batch_size:]

        print(f"Processing batch of {len(current_batch_indices)} images...")

        # Create arguments for batch, ensuring independent seeds
        batch_args = []
        for idx in current_batch_indices:
            seed = random.randint(0, 2**32 - 1)
            batch_args.append((idx, seed))

        # Run batch
        results = pool.map(generate_single_image, batch_args)

        # Re-queue any indices that failed
        success_count = 0
        for r, arg in zip(results, batch_args):
            if r != -1:
                success_count += 1
            else:
                idx = arg[0]
                indices_to_generate.append(idx)

        print(f"Batch completed. Successfully generated {success_count}/{len(current_batch_indices)} images.")

        total_generated += success_count

        # Explicitly clear variables and run garbage collection
        del batch_args
        del results
        gc.collect()

    pool.close()
    pool.join()
    print(f"Data synthesis complete. Total images generated: {total_generated}")

def validate_dataset(output_dir, sample_size=50):
    img_dir = os.path.join(output_dir, "images")
    lbl_dir = os.path.join(output_dir, "labels")

    images = glob.glob(os.path.join(img_dir, "*.jpg"))
    if not images:
        print("No images to validate.")
        return

    sample = random.sample(images, min(sample_size, len(images)))
    print(f"\nValidating {len(sample)} random images...")
    valid = 0
    for img_path in sample:
        base = os.path.basename(img_path)
        lbl_name = base.replace(".jpg", ".txt")
        lbl_path = os.path.join(lbl_dir, lbl_name)
        if not os.path.exists(lbl_path):
            print(f"Missing label for {base}")
            continue
        try:
            with open(lbl_path, "r") as f:
                lines = f.readlines()
                if not lines:
                    print(f"Empty label for {base}")
                    continue
                # Simple format check
                for line in lines:
                    parts = line.strip().split()
                    if len(parts) != 5:
                        print(f"Invalid label format in {lbl_name}: {line}")
                        break
                else:
                    valid += 1
        except Exception as e:
            print(f"Error validating {base}: {e}")

    print(f"Validation finished: {valid}/{len(sample)} passed.")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Generate synthetic Mahjong tile images.")
    parser.add_argument("--count", type=int, default=100000, help="Number of images to generate.")
    parser.add_argument("--batch_size", type=int, default=1000, help="Batch size for generating images.")
    parser.add_argument("--validate_only", action="store_true", help="Only run validation on existing data.")
    args = parser.parse_args()

    base_dir = get_base_dir()
    project_root = os.path.dirname(base_dir)
    out_dir = os.path.join(base_dir, "data", "synthetic")

    if args.validate_only:
        validate_dataset(out_dir)
    else:
        generate_synthetic_data(
            os.path.join(project_root, "mahjong-tiles-image"),
            os.path.join(base_dir, "data", "raw", "backgrounds"),
            out_dir,
            count=args.count,
            batch_size=args.batch_size
        )
        validate_dataset(out_dir)

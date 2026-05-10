import re

with open("ml-pipeline/src/synthesis/tile_synthesizer.py", "r") as f:
    content = f.read()

# Add multiprocessing import
if "import multiprocessing" not in content:
    content = content.replace("import os", "import os\nimport multiprocessing", 1)

# Refactor to use multiprocessing
search = """def generate_synthetic_data(raw_tile_dir, bg_dir, output_dir, count=100000):
    \"\"\"
    Generate synthetic Mahjong tile images with bounding boxes.
    \"\"\"
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
        A.HueSaturationValue(hue_shift_limit=20, sat_shift_limit=30, val_shift_limit=20, p=0.5),
        A.RandomBrightnessContrast(p=0.5)
    ], bbox_params=A.BboxParams(format='yolo', min_visibility=0.1, label_fields=['class_labels']))

    tile_names = list(tiles.keys())

    generated = 0
    attempts = 0
    max_attempts = count * 10

    while generated < count and attempts < max_attempts:"""

replace = """def generate_batch(start_idx, batch_size, tiles, backgrounds, tile_names, transform, bg_w, bg_h, img_out_dir, lbl_out_dir):
    generated = 0
    attempts = 0
    max_attempts = batch_size * 10

    while generated < batch_size and attempts < max_attempts:
        attempts += 1
        bg = random.choice(backgrounds).copy()
        bg_h_actual, bg_w_actual = bg.shape[:2]

        num_tiles = random.randint(5, 14)
        bboxes = []
        labels = []
        placed_boxes = []

        for _ in range(num_tiles):
            t_name = random.choice(tile_names)
            t_img = tiles[t_name]

            scale = random.uniform(0.5, 1.5)
            new_w = int(t_img.shape[1] * scale)
            new_h = int(t_img.shape[0] * scale)
            if new_w <= 0 or new_h <= 0: continue

            t_img_resized = cv2.resize(t_img, (new_w, new_h))

            angle = random.uniform(-180, 180)
            center = (new_w // 2, new_h // 2)
            rot_mat = cv2.getRotationMatrix2D(center, angle, 1.0)
            cos = np.abs(rot_mat[0, 0])
            sin = np.abs(rot_mat[0, 1])
            rot_w = int((new_h * sin) + (new_w * cos))
            rot_h = int((new_h * cos) + (new_w * sin))
            rot_mat[0, 2] += (rot_w / 2) - center[0]
            rot_mat[1, 2] += (rot_h / 2) - center[1]

            t_img_rotated = cv2.warpAffine(t_img_resized, rot_mat, (rot_w, rot_h), flags=cv2.INTER_LINEAR, borderMode=cv2.BORDER_CONSTANT, borderValue=(0, 0, 0, 0))

            max_x = bg_w_actual - rot_w
            max_y = bg_h_actual - rot_h
            if max_x <= 0 or max_y <= 0: continue

            placed = False
            for _ in range(50):
                x = random.randint(0, max_x)
                y = random.randint(0, max_y)
                overlap = False
                for bx1, by1, bx2, by2 in placed_boxes:
                    if not (x + rot_w <= bx1 or x >= bx2 or y + rot_h <= by1 or y >= by2):
                        overlap = True
                        break
                if not overlap:
                    placed = True
                    placed_boxes.append((x, y, x + rot_w, y + rot_h))
                    break

            if not placed: continue

            shadow_x = x + random.randint(5, 15)
            shadow_y = y + random.randint(5, 15)
            if shadow_x + rot_w <= bg_w_actual and shadow_y + rot_h <= bg_h_actual:
                alpha_mask = t_img_rotated[:, :, 3] / 255.0
                shadow_alpha = alpha_mask * random.uniform(0.3, 0.5)
                for c in range(3):
                    bg[shadow_y:shadow_y + rot_h, shadow_x:shadow_x + rot_w, c] = (shadow_alpha * 0 + (1 - shadow_alpha) * bg[shadow_y:shadow_y + rot_h, shadow_x:shadow_x + rot_w, c])

            bg = overlay_image(bg, t_img_rotated, x, y)

            cx = (x + rot_w / 2.0) / bg_w_actual
            cy = (y + rot_h / 2.0) / bg_h_actual
            nw = rot_w / bg_w_actual
            nh = rot_h / bg_h_actual
            bboxes.append([cx, cy, nw, nh])
            labels.append(CLASS_MAPPING[t_name])

        if not bboxes: continue

        try:
            bg_rgb = cv2.cvtColor(bg, cv2.COLOR_BGR2RGB)
            transformed = transform(image=bg_rgb, bboxes=bboxes, class_labels=labels)
            final_img = cv2.cvtColor(transformed['image'], cv2.COLOR_RGB2BGR)
            final_bboxes = transformed['bboxes']
            final_labels = transformed['class_labels']

            if not final_bboxes: continue

            img_idx = start_idx + generated
            img_filename = f"synth_{img_idx:06d}.jpg"
            lbl_filename = f"synth_{img_idx:06d}.txt"

            cv2.imwrite(os.path.join(img_out_dir, img_filename), final_img)
            with open(os.path.join(lbl_out_dir, lbl_filename), "w") as f:
                for bbox, label in zip(final_bboxes, final_labels):
                    f.write(f"{int(label)} {bbox[0]:.6f} {bbox[1]:.6f} {bbox[2]:.6f} {bbox[3]:.6f}\\n")

            generated += 1
        except Exception as e:
            pass

    return generated

def generate_synthetic_data(raw_tile_dir, bg_dir, output_dir, count=100000):
    \"\"\"
    Generate synthetic Mahjong tile images with bounding boxes.
    \"\"\"
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
        A.HueSaturationValue(hue_shift_limit=20, sat_shift_limit=30, val_shift_limit=20, p=0.5),
        A.RandomBrightnessContrast(p=0.5)
    ], bbox_params=A.BboxParams(format='yolo', min_visibility=0.1, label_fields=['class_labels']))

    tile_names = list(tiles.keys())

    num_processes = multiprocessing.cpu_count()
    batch_size = count // num_processes
    batches = [(i * batch_size, batch_size if i < num_processes - 1 else count - i * batch_size) for i in range(num_processes)]

    args_list = [(start_idx, b_size, tiles, backgrounds, tile_names, transform, 0, 0, img_out_dir, lbl_out_dir) for start_idx, b_size in batches]

    with multiprocessing.Pool(num_processes) as pool:
        results = pool.starmap(generate_batch, args_list)

    generated = sum(results)

    # Just to skip the old while loop content, we use dummy condition for parsing
    while False:"""

content = content.replace(search, replace)
# Also need to comment out the old while loop logic up to the end of generate_synthetic_data
# Wait, I didn't replace the rest of the old while loop. Let me do a proper regex replace.

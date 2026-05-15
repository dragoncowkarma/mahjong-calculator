import os
import random
import shutil
from pathlib import Path

def prepare_dataset(synthetic_dir, processed_dir, train_ratio=0.8):
    """Split synthetic data into train/val sets for YOLO training."""
    synthetic_dir = Path(synthetic_dir)
    processed_dir = Path(processed_dir)
    
    img_dir = synthetic_dir / "images"
    lbl_dir = synthetic_dir / "labels"
    
    images = sorted(list(img_dir.glob("*.jpg")))
    random.seed(42)
    random.shuffle(images)
    
    split_idx = int(len(images) * train_ratio)
    train_images = images[:split_idx]
    val_images = images[split_idx:]
    
    for split, split_images in [("train", train_images), ("val", val_images)]:
        split_img_dir = processed_dir / split / "images"
        split_lbl_dir = processed_dir / split / "labels"
        split_img_dir.mkdir(parents=True, exist_ok=True)
        split_lbl_dir.mkdir(parents=True, exist_ok=True)
        
        print(f"Processing {split} split: {len(split_images)} images")
        
        for img_path in split_images:
            lbl_path = lbl_dir / (img_path.stem + ".txt")
            if lbl_path.exists():
                # Use symlinks to save space and time
                try:
                    os.symlink(img_path.absolute(), split_img_dir / img_path.name)
                    os.symlink(lbl_path.absolute(), split_lbl_dir / lbl_path.name)
                except FileExistsError:
                    pass
            else:
                print(f"Warning: Label not found for {img_path}")

if __name__ == "__main__":
    # Adjust paths relative to ml-pipeline/
    base_dir = Path(__file__).parent.parent.parent
    prepare_dataset(
        synthetic_dir=base_dir / "data" / "synthetic",
        processed_dir=base_dir / "data" / "processed"
    )

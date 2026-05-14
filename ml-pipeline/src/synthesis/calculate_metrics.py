import os
import cv2
import numpy as np
import random
from skimage.metrics import structural_similarity as ssim
import argparse

def calculate_ssim_scores(synthetic_dir, real_dir, sample_size=100):
    """Calculate SSIM scores between synthetic images and real reference images."""
    if not os.path.exists(synthetic_dir):
        print(f"Error: Synthetic directory {synthetic_dir} not found.")
        return
    if not os.path.exists(real_dir) or not os.listdir(real_dir):
        print(f"Warning: Real reference directory {real_dir} is empty or missing. Cannot calculate metrics.")
        return

    synth_files = [f for f in os.listdir(synthetic_dir) if f.endswith(".jpg")]
    real_files = [f for f in os.listdir(real_dir) if f.endswith((".jpg", ".png", ".jpeg"))]

    if not synth_files:
        print("No synthetic images found.")
        return

    sample_size = min(sample_size, len(synth_files), len(real_files))
    synth_sample = random.sample(synth_files, sample_size)
    real_sample = random.sample(real_files, sample_size)

    scores = []
    for s_file, r_file in zip(synth_sample, real_sample):
        s_img = cv2.imread(os.path.join(synthetic_dir, s_file), cv2.IMREAD_GRAYSCALE)
        r_img = cv2.imread(os.path.join(real_dir, r_file), cv2.IMREAD_GRAYSCALE)

        if s_img is None or r_img is None:
            continue

        # Resize to common size for comparison
        r_img = cv2.resize(r_img, (s_img.shape[1], s_img.shape[0]))

        score, _ = ssim(s_img, r_img, full=True)
        scores.append(score)

    if scores:
        avg_score = np.mean(scores)
        print(f"Average SSIM score (n={len(scores)}): {avg_score:.4f}")
        return avg_score
    else:
        print("Could not calculate any scores.")
        return 0.0

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Calculate quality metrics for synthetic data.")
    parser.add_argument("--synth_dir", type=str, default="ml-pipeline/data/synthetic/images")
    parser.add_argument("--real_dir", type=str, default="ml-pipeline/data/real")
    parser.add_argument("--sample", type=int, default=100)
    args = parser.parse_args()

    # Resolve paths relative to project root if necessary
    # Assuming script is run from project root
    calculate_ssim_scores(args.synth_dir, args.real_dir, args.sample)

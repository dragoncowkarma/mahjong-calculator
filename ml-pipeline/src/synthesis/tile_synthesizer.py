import os
import cv2
import numpy as np
from PIL import Image

def generate_synthetic_data(raw_tile_dir, bg_dir, output_dir, count=1000):
    """
    마작 패 이미지와 배경 이미지를 합성하여 학습용 데이터를 생성합니다.
    """
    print(f"Generating {count} synthetic images...")
    # TODO: Implement synthesis logic
    pass

if __name__ == "__main__":
    generate_synthetic_data("data/raw/tiles", "data/raw/backgrounds", "data/synthetic")

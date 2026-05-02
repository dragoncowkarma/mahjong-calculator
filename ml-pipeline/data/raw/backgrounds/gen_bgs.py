import cv2
import numpy as np
import os

os.makedirs("ml-pipeline/data/raw/backgrounds", exist_ok=True)
for i in range(5):
    bg = np.random.randint(0, 256, (640, 640, 3), dtype=np.uint8)
    bg = cv2.GaussianBlur(bg, (101, 101), 0)
    cv2.imwrite(f"ml-pipeline/data/raw/backgrounds/bg_{i}.jpg", bg)

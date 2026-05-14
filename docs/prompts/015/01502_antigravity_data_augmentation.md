# Task 01502: Synthetic Data Domain Adaptation

## Objective
Enhance the data synthesis pipeline to minimize the domain gap between purely synthetic Mahjong tile images and real-world camera captures, ensuring the YOLO model will train on high-variance data.

## Scope
- Files: `ml-pipeline/src/synthesis/...` (specifically augmentation scripts and config files).
- Constraint: Do NOT initiate any model training (Phase 2). Focus strictly on data generation and augmentation.

## Tasks
1. **Real-World Noise Simulation:** Update the image augmentation pipeline to introduce realistic noise patterns. This includes:
    - **Lighting Variances:** Simulate harsh directional lighting, soft ambient lighting, and dynamic shadows cast by hands or other objects.
    - **Camera Artifacts:** Add slight motion blur, lens distortion, and varying focal lengths.
2. **Occlusion & Clutter:** Implement augmentation logic to simulate overlapping tiles (e.g., a discarded tile partially covering another) and visual clutter (e.g., table textures, chips, or hands partially obscuring the tiles).
3. **Wear & Tear:** Apply subtle texture maps to simulate worn, dirty, or faded Mahjong tiles.
4. **Quality Metrics:** Implement a script to calculate Structural Similarity Index (SSIM) or a similar metric between a small sample of real reference images and the augmented synthetic images to quantify the domain gap reduction.

## Definition of Done (DoD)
1. The synthesis script executes successfully without errors (`python3 ml-pipeline/src/synthesis/generate.py` or equivalent).
2. A new test batch of 10,000 augmented images is generated and verified for YOLO format integrity.
3. The image quality metrics script outputs a measurable improvement or baseline score.
4. Code changes adhere strictly to the rules defined in `AGENTS.md` and `SUMMARY.xml`.

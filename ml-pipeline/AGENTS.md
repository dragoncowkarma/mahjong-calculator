# ML Pipeline Protocol (ml-pipeline/AGENTS.md)

## 1. Role & Module Invariants (Priority #1)
1. **AI Pipeline Standard**: Use `YOLOv8` or `v11 Nano` architectures; all models MUST be exported to `TFLite` or `CoreML`.
2. **Data Integrity**: Generate synthetic images for all 34 Mahjong tile types using `src/synthesis` before training.
3. **Reproducibility**: Maintain configuration files for every training run to ensure results can be `replicated`.

## 2. Technical Stack (Enforced)
1. **Core**: `PyTorch` and `Ultralytics` for model development and training.
2. **Data**: `OpenCV`, `Albumentations`, and `NumPy` for image processing and augmentation.
3. **Export**: `CoreMLTools` and `TFLite-Support` for mobile model optimization.

## 3. Coding & Style Standards
1. **Python Style**: Adhere to `PEP8` standards strictly. Enforce with `ruff check .`.
2. **Workflow**: Follow the sequence `synthesis` -> `training` -> `export` for any model updates.

## When Writing Code
1. **Model Scope**: Focus exclusively on `YOLO` Nano variants to ensure performance on mobile devices.
2. **Synthesis**: Update the synthesis scripts if new Mahjong tile types or variations are identified via `src/synthesis`.
3. **Verification**: Run `ruff check .` after every script modification to maintain code quality.
4. **Optimization**: Validate that exported models meet the target latency requirements via `TFLite` or `CoreML`.

## When Finishing a Task (Definition of Done)
1. **Code Quality**: All modified Python scripts pass `ruff check .` with zero violations.
2. **Model Validation**: Exported models are verified to be functional in `TFLite` and `CoreML` formats.
3. **Success Criteria**: Task is complete when the training/synthesis pipeline runs to completion and `exit 0` is achieved.

## Escalation & Safety Rules
1. **When Blocked**: If training fails to converge or results in poor accuracy, `STOP` and audit the synthetic data.
2. **Never**: Do not delete existing model weights or training logs without creating a `backup`.
3. **Never**: Never commit large datasets directly to the repository; use the `data/` directory as a local cache.

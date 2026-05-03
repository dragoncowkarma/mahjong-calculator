# 🎯 System Role
You are a Senior ML Engineer responsible for building high-performance object detection models.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (ML)
- Module: ml-pipeline
- Milestone: M05
</context>

# 🛠️ Task
<task>
1. Configure `yolo_config.yaml` in `ml-pipeline/src/training/` for 34 Mahjong tile classes.
2. Train YOLOv8 or YOLOv11 Nano model using the synthetic dataset generated in Phase 1.
3. Tune hyperparameters to achieve mAP 95% or higher on the validation set.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Save training logs and weights in the specified `models/` directory.

# 💻 Input
<input_data>
ml-pipeline/src/training/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Dataset readiness check
- Hyperparameter selection rationale
</thinking>

<implementation>
- Training configuration files and execution scripts
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

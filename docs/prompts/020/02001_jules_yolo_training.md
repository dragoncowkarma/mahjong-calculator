# 🎯 System Role
You are a Senior ML Engineer responsible for training high-performance, mobile-optimized object detection models for real-time Mahjong tile recognition.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (ML)
- Module: ml-pipeline (src/training)
- Milestone: 02001
- Prerequisites: Phase 1 Quality Gate passed (01005). 100k+ YOLO dataset verified.
- Reference: `docs/specs/10_kpi_and_metrics.md` (mAP ≥ 95% gate), `docs/specs/03_application_logic.md` (34 tile classes)
</context>

# 🛠️ Task
<task>
1. **Pre-flight**: Read `ml-pipeline/AGENTS.md` for AI/ML development standards.
2. **Configuration**: Configure `yolo_config.yaml` in `ml-pipeline/src/training/` for **34** Mahjong tile classes (Red Five is NOT a separate class — handled via post-processing).
3. **Training**: Train YOLOv8 or YOLOv11 Nano model using the synthetic dataset generated in Phase 1 (01003).
4. **Hyperparameter Tuning**: Tune hyperparameters to achieve **mAP ≥ 95%** on the validation set.
5. **Logging**: Save all training logs, metrics, and best weights in `ml-pipeline/models/`.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. Save training logs and weights in the specified `models/` directory.
3. [CRITICAL] Target: mAP ≥ 95% on validation set — this is a Phase 2 Quality Gate requirement.
4. Model architecture MUST be Nano variant for mobile deployment (target model size ≤ 10MB after export).
</constraints>

# 💻 Input
<input_data>
ml-pipeline/src/training/
ml-pipeline/data/ (verified dataset from 01003)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Dataset readiness check and class distribution analysis
- Hyperparameter selection rationale
- Training convergence analysis
</thinking>

<implementation>
- Training configuration files and execution scripts
- Training logs and final metrics summary
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] mAP ≥ 95% achieved on validation set
- [ ] Model weights saved to ml-pipeline/models/
- [ ] EOF empty line completed
</verification>
</output_format>

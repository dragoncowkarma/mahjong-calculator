# 🎯 System Role
You are a Senior KMP Engineer responsible for mobile ML model optimization, cross-platform format conversion, and inference quality assurance.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: ml-pipeline/src/export → app/composeApp resources
- Milestone: 02002
- Prerequisites: 02001 completed (YOLO model trained, mAP ≥ 95%)
- Reference: `docs/specs/06_technical_architecture.md` (model size ≤ 10MB, performance SLA)
</context>

# 🛠️ Task
<task>
1. **Pre-flight**: Verify the trained model from 02001 exists and meets mAP ≥ 95%.
2. **Conversion**: Convert the trained YOLO model to:
   - ONNX intermediate format
   - TFLite (FP16 quantized) for Android
   - CoreML for iOS
3. **Size Verification**: Ensure each exported model is ≤ 10MB.
4. **Accuracy Parity**: Run the same validation set on both TFLite and CoreML exports. Verify accuracy difference is ≤ 1%.
5. **Integration**: Place exported models into `app/composeApp/src/commonMain/composeResources/`.
6. **Versioning**: Establish a model versioning scheme (e.g., `mahjong_detector_v1.0.tflite`).
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. Ensure binary file integrity after integration.
3. [CRITICAL] Model size ≤ 10MB per format — Quality Gate requirement.
4. [CRITICAL] TFLite ↔ CoreML accuracy parity ≤ 1% difference.
</constraints>

# 💻 Input
<input_data>
ml-pipeline/src/export/
ml-pipeline/models/ (trained model from 02001)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Conversion process analysis and format-specific considerations
- Quantization strategy (FP16 vs INT8) trade-off analysis
- Resource management and versioning strategy
</thinking>

<implementation>
- Model conversion scripts and resource integration code
- Benchmark results: size, accuracy per format
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Model size ≤ 10MB (TFLite and CoreML)
- [ ] Accuracy parity ≤ 1% between formats
- [ ] EOF empty line completed
</verification>
</output_format>

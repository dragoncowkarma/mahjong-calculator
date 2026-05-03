# 🎯 System Role
You are a Senior KMP Architect responsible for cross-platform inference logic.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain
- Milestone: M07
</context>

# 🛠️ Task
<task>
1. Define the `MahjongDetector` interface and platform-specific `expect/actual` declarations for ONNX/TFLite runtime.
2. Implement post-processing logic (NMS, Box decoding) in `commonMain` to ensure consistency.
3. Validate inference results using sample images.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Maintain high performance for real-time camera inference.

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ml/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- expect/actual design pattern analysis
- Post-processing logic implementation plan
</thinking>

<implementation>
- Interface definitions and platform implementations
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

# 🎯 System Role
You are a Senior KMP Engineer responsible for real-time video processing.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/ui/camera
- Milestone: M08
</context>

# 🛠️ Task
<task>
1. Implement the `CameraPreview` component for Android (CameraX) and iOS (AVFoundation) using `expect/actual`.
2. Connect the camera frame buffer to the `MahjongDetector` for real-time tile detection.
3. Handle device rotation and different aspect ratios correctly.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Minimize frame drop during inference.

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/camera/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Platform-specific camera implementation plan
- Video buffer processing strategy
</thinking>

<implementation>
- Camera components and pipeline connection code
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

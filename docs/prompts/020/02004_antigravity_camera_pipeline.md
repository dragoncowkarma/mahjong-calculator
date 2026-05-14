# 🎯 System Role
You are a Senior KMP Engineer responsible for real-time camera integration, video frame processing, and platform-specific hardware abstraction.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app (commonMain + platform camera)
- Milestone: 02004
- Prerequisites: 02003 completed (MahjongDetector interface and NMS post-processing ready)
- Reference: `docs/specs/06_technical_architecture.md` (min OS: Android 8.0 / iOS 16.0)
</context>

# 🛠️ Task
<task>
1. **Pre-flight**: Review existing `TileRecognitionScreen.kt` and `ImagePicker.kt` interfaces.
2. **Camera Implementation**: Implement `CameraPreview` for Android (CameraX) and iOS (AVFoundation) using `expect/actual`.
3. **Frame Pipeline**: Connect the camera frame buffer to the `MahjongDetector` for real-time tile detection.
4. **Orientation Handling**: Handle device rotation and different aspect ratios correctly.
5. **Pre-processing**: Implement frame rotation, scaling, and normalization in `commonMain`.
6. **Fallback**: Ensure gallery image input remains functional as a fallback path.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] Minimize frame drop during inference — target ≥ 15fps processing rate.
3. Platform-specific code ONLY for hardware camera access. All pre-processing in `commonMain`.
4. Respect minimum OS versions: Android API 26, iOS 16.0.
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/ (TileRecognitionScreen, ImagePicker)
app/composeApp/src/androidMain/
app/composeApp/src/iosMain/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Platform-specific camera implementation plan (CameraX vs AVFoundation)
- Video buffer processing and frame drop mitigation strategy
- Orientation and aspect ratio handling approach
</thinking>

<implementation>
- Camera components, frame pipeline, and pre-processing code
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Camera preview functional on both platforms
- [ ] Frame processing rate ≥ 15fps verified
- [ ] EOF empty line completed
</verification>
</output_format>

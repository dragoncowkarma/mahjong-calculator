# 020 - AI Model Training & Optimization: Execution Command

### 1. [Jules] [Parallel-Ready: Scope A] YOLOv8/v11 Nano Training
- **Scope**: `ml-pipeline/src/training/`
- **Action**:
    - Configure `yolo_config.yaml` for 34 Mahjong tile classes.
    - Execute the training loop using the synthetic dataset generated in Phase 1.
    - Aim for mAP 95%+ on the validation dataset.

---

### 2. [Antigravity: Gemini 3.1 Pro] Model Export & Optimization
- **Task**: Prepare trained weights for mobile inference.
- **Action**:
    - Export trained weights to `TFLite` (Android) and `CoreML` (iOS).
    - Apply FP16 quantization to minimize inference latency on mobile hardware.
    - Verify file integrity and metadata for both exported formats.

---

### 3. [Jules] [Parallel-Ready: Scope B] Inference Engine Interfaces
- **Scope**: `app/composeApp/src/commonMain/kotlin/.../models/`
- **Action**:
    - Define `TileDetectionModel` interface and `DetectionResult` data models.
    - Implement the coordinate mapper to translate normalized [0,1] bounding boxes to screen DP.

---

### 4. [Antigravity: Claude Opus 4.6] Real-Time Camera Pipeline
- **Task**: Implement native stream handling in `commonMain`.
- **Action**:
    - Build the `CameraFeedManager` and `ImageAnalyzer` logic.
    - Implement frame buffer rotation and scaling for the YOLO input (640x640).
    - Ensure zero-copy buffer handling to maintain 30+ FPS performance.

---

### 5. [Antigravity: Gemini 3.1 Pro] Dependency Injection Wiring
- **Task**: Connect platform-specific detectors via Kotlin-Inject.
- **Action**:
    - Implement `AndroidTileDetector` and `IosTileDetector` using the exported models.
    - Wire all components into the `AppComponent` for cross-platform access.

---

### 6. [Gemini CLI] Build & Integrity Verification
- **Task**: Final build check.
- **Action**:
    - Run `./gradlew :app:composeApp:compileKotlinCommon` to verify DI and interface consistency.
    - Audit the binary sizes of the exported ML models.

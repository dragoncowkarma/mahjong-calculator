# 030 - UI Integration & UX Polish: Execution Command

### 1. [Antigravity: Claude Opus 4.6] Tile Correction Panel
- **Task**: Build the manual correction interface in `commonMain`.
- **Action**:
    - Implement `TileCorrectionPanel` with a selectable grid of 34 Mahjong tiles.
    - Build the logic for manual tile replacement/swapping within the detected hand state.
    - Ensure StateFlow-based UI updates for real-time reactivity.

---

### 2. [Jules] [Parallel-Ready] Overlay Visuals & Result Dashboard
> [!TIP]
> Jules can execute these two UI scopes concurrently as they target different component packages.

**[Scope A] `app/composeApp/src/commonMain/kotlin/.../ui/overlay/`**
- **Action**:
    - Build the `BoundingBoxOverlay` using Compose `Canvas`.
    - Implement smoothing/interpolation logic for box movement tracking.
    - Ensure 60FPS overlay rendering on top of the camera preview.

**[Scope B] `app/composeApp/src/commonMain/kotlin/.../ui/`**
- **Action**:
    - Implement `ResultDashboard` using `LazyColumn` for Yaku and Fu breakdown.
    - Create detail components for each Yaku with clear visual descriptions.

---

### 3. [Antigravity: Gemini 3.1 Pro] Recomposition Audit & Platform Polish
- **Task**: UI Performance tuning and platform-specific refinement.
- **Action**:
    - Audit `MutableState` usage to identify and eliminate redundant recompositions.
    - Refine platform-specific UI nuances (iOS Safe Area, Android System Bars).
    - Implement smooth cross-fade transitions between Detection and Result states.

---

### 4. [Gemini CLI] UI & Navigation Testing
- **Task**: Automated verification of the visual stack.
- **Action**:
    - Execute Paparazzi or Roborazzi screenshot tests for all new UI components.
    - Run `./gradlew connectedCheck` to verify cross-screen navigation flows.
    - Confirm exactly one blank line at the end of all modified UI files.

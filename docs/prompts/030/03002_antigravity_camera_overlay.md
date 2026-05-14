# 🎯 System Role
You are a Senior UI/UX Engineer specializing in real-time camera overlays, coordinate mapping, and confidence-driven UX interactions.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/ui (camera overlay)
- Milestone: 03002
- Prerequisites: 03001 (design system) AND 02004 (camera pipeline) completed
- Reference: `docs/specs/02_core_workflows.md` (confidence threshold 0.7, fallback strategy), `docs/specs/05_ui_ux_design.md`
</context>

# 🛠️ Task
<task>
1. **Bounding Box Overlay**: Implement `BoundingBoxOverlay` using Compose `Canvas` to visualize detected tiles on the camera preview.
2. **Coordinate Mapping**: Map normalized inference coordinates to actual display coordinates, handling aspect ratio differences.
3. **Smooth Animations**: Add interpolation-based animations for box updates to reduce visual flicker.
4. **Confidence Display**: Show confidence scores using the design system's color coding:
   - ≥ 0.9: Green (high confidence)
   - 0.7~0.9: Yellow (medium confidence)
   - < 0.7: Red + auto-trigger correction panel
5. **Tile Labels**: Display detected tile class labels using `TileCard` mini-components.
6. **Auto-Correction Trigger**: When average confidence < 0.7, automatically show the correction panel overlay.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] Overlay MUST be perfectly aligned with underlying camera frames across device orientations.
3. Confidence threshold 0.7 is defined in `docs/specs/02_core_workflows.md` — do not hardcode, use a constant.
4. Use design system components (from 03001) for all visual elements.
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/ (TileRecognitionScreen, theme)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Coordinate mapping strategy (inference → display, handling aspect ratios)
- Animation approach for smooth box transitions
- Confidence-driven UX flow design
</thinking>

<implementation>
- BoundingBoxOverlay, coordinate mapper, confidence UI logic
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Overlay aligns with camera frames in portrait and landscape
- [ ] Low-confidence auto-correction trigger works
- [ ] EOF empty line completed
</verification>
</output_format>

# 🎯 System Role
You are a Senior UI/UX Engineer specializing in Augmented Reality overlays.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/ui/camera
- Milestone: M10
</context>

# 🛠️ Task
<task>
1. Implement the `BoundingBoxOverlay` to visualize detected tiles on top of the camera preview.
2. Add smooth animations for box updates to reduce flicker.
3. Show confidence scores and tile labels using the project's design system.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Ensure the overlay is perfectly aligned with the underlying camera frames.

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/camera/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Coordinate mapping (Inference -> UI) strategy
- Animation and flicker reduction plan
</thinking>

<implementation>
- Bounding box implementation and animation logic
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

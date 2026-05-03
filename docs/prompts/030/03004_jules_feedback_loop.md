# 🎯 System Role
You are a Senior Software Engineer responsible for system reliability and user feedback loops.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/logic/feedback
- Milestone: M12
</context>

# 🛠️ Task
<task>
1. Implement a system to allow users to manually correct detection errors (Misidentified tiles).
2. Create a data structure to store these corrections for future model fine-tuning.
3. Integrate an 'Auto-Correction' logic that uses Mahjong rules to suggest the most likely tile if confidence is low.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Feedback collection must be non-intrusive to the UX.

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/logic/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Feedback UI design pattern analysis
- Auto-correction algorithm plan
</thinking>

<implementation>
- Feedback logic and correction data models
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

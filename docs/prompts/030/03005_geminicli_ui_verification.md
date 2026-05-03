# 🎯 System Role
You are a Senior QA and Stability Engineer responsible for UI/UX integrity.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP UI/UX)
- Milestone: M11 (UI Verification)
</context>

# 🛠️ Task
<task>
1. Audit the `app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/` directory for potential memory leaks in Compose.
2. Verify if the `MahjongTheme` is correctly applied to all screens.
3. Check for UI layout overflows on smaller screen sizes.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Prohibited] Do not perform code modification.

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Compose best-practice audit plan
- Theme consistency check results
</thinking>

<implementation>
- Audit report and UI stability recommendations
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

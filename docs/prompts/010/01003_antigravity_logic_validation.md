# 🎯 System Role
You are a Senior KMP Engineer responsible for logic stability and data consistency.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP/ML)
- Module: app (commonMain)
- Milestone: M03
</context>

# 🛠️ Task
Refine scoring edge cases and localized metadata.
<task>
1. **Pre-flight**: Review root `AGENTS.md` and `app/AGENTS.md` for logic-layer protocols.
2. **Logic Review**: Review `MahjongCalculator` for edge cases like Furiten, Pinfu Fu calculation, and complex wait patterns.
3. **Data Mapping**: Map all Yaku names and descriptions to the `YakuData.kt` model using Korean strings.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Use Korean strings for Yaku names and descriptions.

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/calculator/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Edge case analysis
- Yaku mapping strategy
</thinking>

<implementation>
- Updated logic and data models
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

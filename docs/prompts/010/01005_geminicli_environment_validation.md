# 🎯 System Role
You are a Senior QA and Environment Engineer responsible for project integrity.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP/ML)
- Milestone: M05 (Cleanup & Validation)
</context>

# 🛠️ Task
Final structure check and boilerplate removal.
<task>
1. **Pre-flight**: Verify `SUMMARY.xml` is up-to-date.
2. **Hierarchy Check**: Execute `ls -R` to verify the hierarchy matches `SUMMARY.xml`.
3. **Cleanup**: Remove redundant boilerplate files from IDE templates via `rm -rf`.
4. **Validation**: Run `./gradlew build` to confirm zero dependency conflicts.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Prohibited] Do not delete core source code.

# 💻 Input
<input_data>
Project Root
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Hierarchy verification results
- List of removed boilerplate files
</thinking>

<implementation>
- Commands executed and build results
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

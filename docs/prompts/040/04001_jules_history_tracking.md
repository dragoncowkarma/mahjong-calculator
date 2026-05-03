# 🎯 System Role
You are a Senior Software Engineer responsible for data persistence and analytics.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/data/history
- Milestone: M13
</context>

# 🛠️ Task
<task>
1. Implement a local database (e.g., SQLDelight) to store Mahjong session history and results.
2. Create a repository layer to manage CRUD operations for history records.
3. Optimize database schema for fast querying of statistical data.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Ensure platform-specific DB drivers are correctly implemented.

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/data/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- SQLDelight schema design plan
- Platform-specific driver integration strategy
</thinking>

<implementation>
- Database schema, repository, and platform implementation code
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

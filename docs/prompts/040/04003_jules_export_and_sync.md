# 🎯 System Role
You are a Senior Software Engineer responsible for cloud integration and data portability.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/logic/sync
- Milestone: M15
</context>

# 🛠️ Task
<task>
1. Implement a feature to export session history to CSV or JSON formats.
2. Integrate a cloud sync mechanism (e.g., Firebase or a custom REST API) to synchronize history across multiple devices.
3. Handle offline-first scenarios and data conflict resolution during sync.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Ensure user data privacy and secure transmission.

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/logic/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Cloud sync architecture and conflict resolution plan
- Export/Import logic implementation strategy
</thinking>

<implementation>
- Sync logic, export features, and API integration code
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] EOF empty line and comment cleanup completed
</verification>
</output_format>

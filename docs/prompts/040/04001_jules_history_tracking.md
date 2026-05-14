# 🎯 System Role
You are a Senior Backend/Data Engineer responsible for local database schema design, migration strategy, and data persistence for session tracking.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (v2.0 feature)
- Module: app/commonMain/data
- Milestone: 04001
- Prerequisites: v1.0-beta launched successfully
- Reference: `docs/specs/04_key_features.md` (v2.0 session tracking), `docs/specs/06_technical_architecture.md` (SQLDelight)
</context>

# 🛠️ Task
<task>
1. **Schema Design**: Design SQLDelight database schema for:
   - `GameSession` (session metadata: date, players, round count)
   - `ScoreRecord` (individual scoring events within a session)
   - `CorrectionLog` (optional: user corrections for future ML training)
2. **Repository Layer**: Implement `GameSessionRepository` using coroutines/Flow for reactive data access.
3. **Migration Strategy**: Plan forward-compatible schema versioning for future cloud sync (v2.0+).
4. **Integration**: Wire the repository into the DI graph via `AppComponent`.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] Database access MUST NOT block the main/UI thread.
3. Schema must be forward-compatible for potential cloud sync.
4. All SQL in `commonMain` via SQLDelight (no platform-specific DB code).
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/data/
app/composeApp/src/commonMain/sqldelight/ (new)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Schema design rationale and normalization decisions
- Migration versioning strategy
- Flow/coroutine integration pattern
</thinking>

<implementation>
- SQLDelight .sq files, Repository implementation, DI wiring
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] No main thread blocking
- [ ] Schema versioned and migration-ready
- [ ] EOF empty line completed
</verification>
</output_format>

# 🎯 System Role
You are a Senior Software Engineer responsible for advanced calculation features, data export pipelines, and cloud synchronization prototyping.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (v2.0 feature)
- Module: app/commonMain/logic + data
- Milestone: 04003
- Prerequisites: 04001 (history tracking DB) completed
- Reference: `docs/specs/04_key_features.md` (Shanten analysis, cloud sync)
</context>

# 🛠️ Task
<task>
1. **Shanten Calculator**: Implement basic Shanten (向聴数) analysis:
   - Given a hand of 13 tiles, calculate the minimum number of tile replacements needed to reach Tenpai.
   - Show which tiles to discard and which tiles to wait for.
2. **Data Export**: Implement session data export in CSV/JSON format for:
   - Session summaries
   - Detailed scoring records
3. **Cloud Sync Prototype**: Evaluate and prototype cloud synchronization using:
   - Candidate: Firebase Firestore or Supabase
   - Implement basic CRUD operations for session data
   - Offline-first with conflict resolution strategy
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. Shanten calculation MUST be in `commonMain` (no platform-specific code).
3. Cloud sync is a PROTOTYPE — do not introduce production-grade dependencies yet.
4. Export format must be human-readable and importable by spreadsheet software.
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/calculator/ (Shanten)
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/data/ (export + sync)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Shanten algorithm selection (brute force vs optimized lookup table)
- Export format design
- Cloud sync architecture evaluation (Firebase vs Supabase)
</thinking>

<implementation>
- Shanten calculator, export logic, sync prototype
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Shanten calculation correct for standard hands
- [ ] Export produces valid CSV/JSON
- [ ] EOF empty line completed
</verification>
</output_format>

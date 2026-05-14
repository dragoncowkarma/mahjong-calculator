# 🎯 System Role
You are a Senior Software Engineer responsible for building intelligent feedback collection systems, low-confidence auto-correction algorithms, and future model fine-tuning data pipelines.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/logic
- Milestone: 03004
- Prerequisites: 03002 (camera overlay) completed. Can run in parallel with 03003.
- Reference: `docs/specs/02_core_workflows.md` (fallback strategy), `docs/specs/09_risk_management.md` (R08 spatial layout failure)
</context>

# 🛠️ Task
<task>
1. **Manual Correction System**: Implement a system for users to manually correct misidentified tiles via the `TileCorrectionPanel`.
2. **Correction Data Model**: Create a data structure to store corrections (original prediction vs user correction) for future model fine-tuning:
   ```
   CorrectionRecord(originalTile, correctedTile, confidence, timestamp, imageRegion)
   ```
3. **Auto-Correction Algorithm**: Implement rule-based auto-correction that uses Mahjong rules to suggest the most likely tile when confidence is low:
   - Check tile count constraints (max 4 of each tile)
   - Suggest based on partial hand pattern (e.g., if 2 tiles of a triplet detected, suggest the third)
4. **Non-Intrusive UX**: Corrections must not block the main calculation flow — suggestions appear as gentle nudges, not modal dialogs.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] Feedback collection MUST be non-intrusive to the UX — no modal blocking.
3. Correction data is stored locally only (no server upload in v1.0).
4. Auto-correction is a SUGGESTION only — user always has final control.
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/logic/
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/models/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Correction data model design for future fine-tuning compatibility
- Auto-correction algorithm: rule-based heuristics
- UX pattern for non-intrusive suggestions
</thinking>

<implementation>
- Feedback logic, correction data models, auto-correction algorithm
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Correction flow is non-blocking
- [ ] Auto-correction respects tile count constraints
- [ ] EOF empty line completed
</verification>
</output_format>

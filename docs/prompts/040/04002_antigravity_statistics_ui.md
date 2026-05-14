# 🎯 System Role
You are a Senior UI/UX Engineer responsible for data visualization, statistical dashboards, and interactive chart components.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (v2.0 feature)
- Module: app/commonMain/ui/stats
- Milestone: 04002
- Prerequisites: 04001 (history tracking DB) completed
- Reference: `docs/specs/04_key_features.md` (v2.0 statistics)
</context>

# 🛠️ Task
<task>
1. **Statistics Screen**: Implement `StatisticsScreen` with:
   - Session history list (date, participants, final scores)
   - Yaku frequency chart (most/least achieved Yaku)
   - Average score trend graph
2. **Compose Charts**: Implement lightweight charting using Compose Canvas (no external charting library).
3. **Data Binding**: Connect to `GameSessionRepository` via `ScreenModel` and reactive Flows.
4. **Empty State**: Design an engaging empty state for users with no session history.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] Maintain 60fps during chart animations.
3. No external charting libraries — use Compose Canvas for all visualizations.
4. All UI text in Korean.
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/stats/ (new)
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/data/ (repository from 04001)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Dashboard layout design and information hierarchy
- Compose Canvas charting approach
- Empty state UX design
</thinking>

<implementation>
- StatisticsScreen, chart components, ScreenModel
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Charts render smoothly at 60fps
- [ ] Empty state displays correctly
- [ ] EOF empty line completed
</verification>
</output_format>

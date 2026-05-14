# 🎯 System Role
You are a Senior UI/UX Engineer responsible for complex scoring data visualization, navigation flow integration, and unified result presentation.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/ui
- Milestone: 03003
- Prerequisites: 03001 (design system) completed
- Reference: `docs/specs/05_ui_ux_design.md` (ScoreResultScreen — unified result view), `docs/specs/03_application_logic.md` (scoring output format)
</context>

# 🛠️ Task
<task>
1. **Unified Result Screen**: Implement `ScoreResultScreen` that combines:
   - Large typography total score display
   - Distribution table (dealer/non-dealer payment breakdown)
   - Applied Yaku list section (accordion-style, Korean names)
   - Fu breakdown section (collapsible)
2. **Tile Hand Display**: Use `TileCard` components to render the 14-tile winning hand.
3. **Navigation Integration**: Support two entry paths via Voyager:
   - Manual input (YakuCalculationScreen) → ScoreResultScreen
   - Camera recognition (TileRecognitionScreen) → Correction → ScoreResultScreen
4. **Responsive Layout**: Single column on phone, 2-column on tablet (≥ 600dp).
5. **Micro-animations**: Score counting animation, Yaku badge entrance animation.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] High readability on small screens — minimum font size 14sp for score details.
3. All Yaku names and descriptions in Korean (per `docs/specs/06` constraint 5).
4. Use Voyager navigation exclusively (no manual backstack manipulation).
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/ (existing screens, theme)
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/calculator/ (scoring output models)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Unified result screen layout design (single column vs sections)
- Navigation flow for dual entry paths
- Responsive layout strategy for phone vs tablet
</thinking>

<implementation>
- ScoreResultScreen, navigation wiring, animations
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Both navigation entry paths work correctly
- [ ] Responsive layout adapts at 600dp breakpoint
- [ ] All text in Korean
- [ ] EOF empty line completed
</verification>
</output_format>

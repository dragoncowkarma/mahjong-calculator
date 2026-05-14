# 🎯 System Role
You are a Senior UI/UX Engineer responsible for building a cohesive, accessible design system using Material 3 and Compose Multiplatform. Also supports v1.0-alpha lean launch preparation.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (Design System)
- Module: app (commonMain/ui/theme)
- Milestone: 03001
- Prerequisites: Phase 2 Quality Gate passed (02006). For v1.0-alpha track: 01004 completed.
- Reference: `docs/specs/05_ui_ux_design.md` (dark mode first, accessibility), `docs/specs/12_wbs.md` (α track)
</context>

# 🛠️ Task
<task>
1. **Pre-flight**: Review existing UI components and `docs/specs/05_ui_ux_design.md` design principles.
2. **Theme**: Build `MahjongTheme` with signature 'Green & Gold' accent color palette.
   - Dark Mode (default) and Light Mode complete support.
   - Define spacing, typography, and elevation tokens.
3. **Core Components**: Implement reusable components:
   - `TileCard`: Visual representation of a single Mahjong tile (supports accessibility via number/pattern overlay for colorblind users).
   - `YakuBadge`: Badge showing Yaku name (Korean) and Han count.
   - `FuDetailRow`: Row component for Fu breakdown display.
4. **Accessibility**: Ensure minimum touch target 48dp, proper content descriptions.
5. **v1.0-alpha Support**: These components serve both v1.0-alpha (manual input) and v1.0-beta (camera) flows.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] Adhere to Material 3 design guidelines.
3. [CRITICAL] Dark mode is the DEFAULT theme.
4. Minimum touch target: 48dp (accessibility requirement).
5. All user-facing strings in Korean.
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/theme/
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/ (existing screens)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Color palette rationale and Material 3 token mapping
- Component reusability and accessibility design
- v1.0-alpha vs v1.0-beta component sharing strategy
</thinking>

<implementation>
- Theme definition, core component code
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Dark mode renders correctly as default
- [ ] All touch targets ≥ 48dp
- [ ] Components reusable across manual input and camera flows
- [ ] EOF empty line completed
</verification>
</output_format>

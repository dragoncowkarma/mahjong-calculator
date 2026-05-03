# 🤖 Prompt Engineering Standards (docs/prompts/agents.md)

[CRITICAL] All prompt files (`.md`) in `docs/prompts/` MUST strictly adhere to the `Mahjong Calculator Standard Prompt Template`.

## 1. Core Rules
1. **Parallel Execution**: Run parallel tasks ONLY via `jules`.
2. **Gemini CLI Restriction**: NEVER use `Gemini CLI` for `UI` modification or resource tasks due to `MCP` issues.

## 2. Standard Prompt Templates
Select the standard template based on task type.

---

### 2.1. Single-Process Prompt Template
# 🎯 System Role
1. [Refer to Section 3 for Role Definition]

# 📋 Context
1. Read `../../SUMMARY.xml` and `../../REFACTOR_TRACKING.md` before starting.
<context>
- Project: `Mahjong Calculator` (KMP/ML)
- Module: [Module Name]
- Background: [Tasks/REFACTOR_TRACKING entries]
</context>

# 🛠️ Task
Execute instructions per `AGENTS.md`.
<task>
1. Check `../../SUMMARY.xml` for scope overlaps and `../../REFACTOR_TRACKING.md` for debts.
2. Propose `implementation_plan.md` for architectural changes.
3. [Detail core features]
4. Remove resolved items from `../../REFACTOR_TRACKING.md`.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at `EOF`.
2. Explain "Why" in comments, not "What".
3. NEVER leave unrequested boilerplate or `debug logs`.
4. Extract `magic numbers` into constants.
5. DO NOT change function signatures without `Task` instruction.
</constraints>

# 💻 Input
<input_data>
1. [Source code or reference data]
</input_data>

# 📝 Output Format
<output_format>
<thinking>
1. Analyze situation and edge cases.
2. Verify `AGENTS.md` compliance.
</thinking>

<implementation>
1. Use agent tools or `Diff` format.
</implementation>

<verification>
1. `[ ] Context/Refactor Tracking verified`
2. `[ ] EOF empty line and comment cleanup completed`
</verification>
</output_format>

---

### 2.2. Jules Parallel Prompt Template
# 🎯 System Role
1. [Refer to Section 3.1 for Jules Role Definition]

# 📋 Context
1. Read `../../SUMMARY.xml` and `../../REFACTOR_TRACKING.md` before starting.
<context>
- Project: `Mahjong Calculator` (KMP/ML)
- Module: [Module Name]
- Background: [Tasks/REFACTOR_TRACKING entries]
</context>

# 🛠️ Task
Execute instructions per `AGENTS.md`.
<task>
1. Check `../../SUMMARY.xml` and `../../REFACTOR_TRACKING.md`.
2. Implement core features.
3. Remove resolved items from `../../REFACTOR_TRACKING.md`.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at `EOF`.
2. Explain "Why" in comments.
3. **[CRITICAL]** NEVER modify files outside `input_data`.
</constraints>

# 💻 Input
<input_data>
1. `[Source code. MUST NOT overlap with parallel tasks]`
</input_data>

# 📝 Output Format
<output_format>
<thinking>
1. Analyze situation.
2. Verify `Scope Restriction` and conflict avoidance.
3. Verify `AGENTS.md` compliance.
</thinking>
<implementation>
1. Use agent tools or `Diff` format.
</implementation>
<verification>
1. `[ ] Context/Refactor Tracking verified`
2. `[ ] EOF empty line and comment cleanup completed`
3. `[ ] Scope Restriction verified`
</verification>
</output_format>

---

## 3. Agent Role Definitions

### 3.1. Jules (Logic & Implementation)
1. **Role**: `Senior Software Engineer` (10 years experience).
2. **Responsibility**: Complex business logic, scoring engines, `ml-pipeline`.
3. **Note**: Handles `Parallel Processing` exclusively.

### 3.2. Antigravity (KMP, UI & Polish)
1. **Role**: `Senior KMP Engineer` and `UI/UX Designer`.
2. **Responsibility**: `Compose UI`, `Voyager`, `Kotlin-Inject`, platform polish.

### 3.3. Gemini CLI (Validation & Audit)
1. **Role**: `Senior QA Engineer`.
2. **Responsibility**: Log analysis, build stability, `Linter` audits.
3. **Restriction**: NEVER modify `UI/UX`.

## 4. Communication Standards
1. **PR Feedback**: Include `Target File`, `Error Log`, and `AGENTS.md` check instruction.
2. **Build Error**: Include `Compiler Output`, `StackTrace`, and `AGENTS.md` compliance emphasis.
3. **Merge Conflict**: Include `Conflict Blocks` and `master` branch logic explanation.

## 5. Management Rules
1. Copy templates for all new prompt files.
2. Sync templates immediately if `AGENTS.md` changes.

# 🤖 Project Agent Protocol (AGENTS.md)

[CRITICAL] This document defines the base standards for the `Mahjong Calculator` project. Specific rules are located in:
1. `app/AGENTS.md`: KMP Client standards.
2. `ml-pipeline/AGENTS.md`: Python ML Pipeline standards.
3. `docs/AGENTS.md`: Documentation and Planning standards.

## When Starting a Task
1. **Context Awareness**: Read `SUMMARY.xml` prior to accessing any files to prevent redundant operations.
2. **Refactor Tracking**: Check [`REFACTOR_TRACKING.md`](REFACTOR_TRACKING.md) immediately. Resolve entries within scope and delete them from the file once done.
3. **Ambiguity**: If requirements are unclear or contradictory, **Stop** and call `ask_question` or wait for user feedback in planning mode.
4. **Planning**: For complex tasks, architectural changes, or significant deviations, **Stop** and propose an `implementation_plan.md`.

## When Modifying Code
1. **Scope Restriction**: Modifications MUST be strictly limited to files defined in `SUMMARY.xml`.
2. **No Boilerplate**: Prohibit unrequested boilerplate generation; focus on `Task` scope only.
3. **Closure**: Every task MUST result in an `exit 0` state or a valid build.
4. **Mimicry**: Adapt to the established local coding style found in `app/` and `ml-pipeline/`.

## When Blocked
1. **Technical Debt**: If a task requires bypassing a protocol, log it in [`REFACTOR_TRACKING.md`](REFACTOR_TRACKING.md) and report to the `USER`.
2. **Linter Failures**: If the project linter (`agent_md_linter.py`) fails on any modified `AGENTS.md`, fix the anti-patterns immediately.
3. **Never**: Do not delete core architecture files or bypass the `SUMMARY.xml` update requirement.
4. **Never**: Never modify files outside the scope defined in `SUMMARY.xml` without explicit authorization.

## Agent Restrictions
1. **Parallel Execution**: Parallel task processing is EXCLUSIVELY permitted for the `Jules` agent.
2. **Gemini CLI Constraints**: Due to environment/`MCP` limitations, `Gemini CLI` must NOT be used for `UI` modification or resource-heavy tasks.

## Definition of Done (DoD)
A task is complete when ALL of the following pass:
1. `SUMMARY.xml` is updated with all new files and component paths.
2. The project-wide build command `./gradlew build` (for `app/`) or `ruff check .` (for `ml-pipeline/`) exits 0.
3. All modified files end with exactly one blank line (verified by `tail -c 1`).
4. `python3 ~/Desktop/agent-md-linter/agent_md_linter.py AGENTS.md` exits 0 if any protocol file was modified.
5. The code satisfies 100% of the `USER_REQUEST`.

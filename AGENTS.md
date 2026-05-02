# Project Agent Protocol (AGENTS.md)

## 1. Role & Core Invariants (Priority #1)
1. **Context Maintenance**: Read `SUMMARY.xml` before starting any task to prevent unnecessary file access and grasp the architecture.
2. **State Persistence**: Update `SUMMARY.xml` immediately after any structural changes, new component creation, or path modifications.
3. **Operational Integrity**: Adhere to the `AGENTS.md` found in target modules (`app/`, `ml-pipeline/`) for specific environment rules.
4. **Consistency**: Every file MUST end with exactly one blank line. Use `tail -c 1` to verify.

## 2. Technical Stack (Common)
1. **Context**: `SUMMARY.xml` (Project-wide mapping).
2. **Documentation**: `docs/specs/*.md` (Technical specifications).
3. **Protocol**: Root `AGENTS.md` (Common) and module-specific `AGENTS.md`.

## When Starting a Task
1. **Path Resolution**: Read `SUMMARY.xml` to locate components and prevent redundant `ls` or `view_file` calls.
2. **Protocol Check**: Verify core invariants in this file and any `AGENTS.md` in the current working directory.
3. **Pre-Flight**: Run `python3 ~/Desktop/agent-md-linter/agent_md_linter.py AGENTS.md` if any protocol is modified.

## When Finishing a Task (Definition of Done)
A task is complete when ALL of the following pass:
1. `SUMMARY.xml` is updated with all new files and component paths.
2. The project-wide build or module-specific verify command (`./gradlew build` or `ruff check`) exits 0.
3. All new files end with exactly one blank line verified by `tail -c 1`.
4. The modified code passes all functional requirements and `exit 0` is achieved.

## Escalation & Safety Rules
1. **When Blocked**: If a requirement is ambiguous or conflicts with `SUMMARY.xml`, stop and ask for clarification.
2. **Never**: Do not delete core architecture files or bypass the `SUMMARY.xml` update requirement or documentation.
3. **Never**: Never modify files outside the scope defined in `SUMMARY.xml` without explicit authorization.
4. **If Conflict**: If local rules conflict with root rules, prioritize root safety and `Escalation & Safety Rules`.

# 📑 Agent Entry Point (docs/AGENTS.md)

[CRITICAL] This document is the primary entry point for AI agents managing the documentation and planning layer of the `Mahjong Calculator` project. Read the root `../AGENTS.md` for general protocols.

## AI Agent Role Distribution

| Agent | Persona | Task Scope | Reference Model |
| :--- | :--- | :--- | :--- |
| **Jules** | Senior Architect | Logic design, Scoring Engine optimization, ML Pipeline, Python scripts. **Handles Parallel Processing exclusively.** | `Gemini 3.1 Pro` |
| **Antigravity** | KMP Engineer | Compose Multiplatform UI, Voyager & Kotlin-Inject integration, platform interfaces. | `Gemini 3.1 Pro` / `Flash` |
| **Gemini CLI** | QA Engineer | Stability verification, `SUMMARY.xml` integrity, performance benchmarks, Linter audit. **Restriction: Cannot modify UI/UX.** | `Gemini 3.1 Pro` / `Flash` |

## Documentation Management Rules
1. **Standard Format**: All documentation MUST be written in `.md` (`Markdown`) format.
2. **Context Optimization**:
    2.1. **Document Split**: Split docs by `domain` if they exceed `150 lines` or become overly complex.
    2.2. **Discovery**: Register all new or split documents in `../SUMMARY.xml` immediately.
3. **Synchronization**: Bidirectional updates between `docs/specs/` and `docs/prompts/` are MANDATORY.
4. **Bilingual Standard**: Use `Korean` for high-level documentation (PRD, GDD); `English` for technical specs, prompt files, and code comments.

## When Writing Documentation
1. **Relative Linking**: All file references MUST use relative paths (e.g. `../app/`) to ensure portability.
2. **Diagram Integrity**: Use `Mermaid` for visualizations; ensure syntax is valid.
3. **File Integrity**: Ensure exactly `1 empty line` at the end of every file (`EOF`).

## Planning and Task Management
1. **Parallel Processing Assessment**: When new tasks emerge from planning changes, agents MUST evaluate if they are `parallelizable`.
    1.1. **Restricted Scope**: Parallel execution is strictly for the `Jules` agent to handle independent logic or ML tasks.
    1.2. **Registry Synchronization**: Register any parallelizable task in `docs/prompts/parallel_tasks.md` before execution.

## Definition of Done
1. Ensure all newly created or modified documents are correctly listed in `../SUMMARY.xml`.
2. `Verify every internal link uses a valid relative path and resolves correctly.`
3. Run `python3 ~/Desktop/agent-md-linter/agent_md_linter.py AGENTS.md` to ensure zero errors.
4. Confirm the document satisfies 100% of the `USER_REQUEST`.


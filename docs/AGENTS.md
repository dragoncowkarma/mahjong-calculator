# Documentation Protocol (docs/AGENTS.md)

## 1. Role & Module Invariants (Priority #1)
1. **Source of Truth**: Technical specifications in `specs/` MUST reflect the state of `app/` and `ml-pipeline/`.
2. **Relative Linking**: All file references MUST use relative paths (e.g. `../app/`) to ensure portability.
3. **Diagram Integrity**: Use `Mermaid` for visualizations; ensure syntax is valid via `mmdc` or manual check.
4. **Context Mapping**: Every document MUST be tracked in the root `../SUMMARY.xml` with a clear purpose.

## 2. Technical Stack (Enforced)
1. **Format**: GitHub Flavored Markdown for all `.md` files.
2. **Visuals**: `Mermaid` diagrams for hierarchy and data flow.
3. **Structure**: Prefixed naming convention (e.g. `01_overview.md`) for logical ordering.

## 3. Style & Formatting Standards
1. **Headings**: Use hierarchical headings (H1 for title, H2 for main sections). Enforce with `ruff check`.
2. **Mermaid**: Quote node labels containing special characters like `id["Label (Extra)"]`.
3. **Naming**: Use `snake_case` for filenames and `PascalCase` for component references within text.

## When Writing Documentation
1. **Path Resolution**: Use `../` to reference root assets or other modules from the `docs/` directory.
2. **Verification**: Confirm that every new document has a corresponding entry in `../SUMMARY.xml`.
3. **Consistency**: Check `../docs/specs/06_technical_architecture.md` before updating diagrams.
4. **Constraint**: If a document references a deleted component, `STOP` and update the documentation.

## When Finishing a Task (Definition of Done)
A task is complete when ALL of the following pass:
1. All newly created or modified documents are listed in `../SUMMARY.xml`.
2. Every internal link uses a valid relative path (e.g. `../`) and resolves correctly.
3. Mermaid diagrams render without syntax errors via `mmdc`.
4. The document ends with exactly one blank line verified by `tail -c 1`.

## Escalation & Safety Rules
1. **When Blocked**: If technical details are ambiguous, stop and consult the source code in `../app/`.
2. **Never**: Do not use absolute paths (e.g. `/Users/`) in any documentation file.
3. **Never**: Never delete specification files without updating `specs/07_development_milestones.md`.

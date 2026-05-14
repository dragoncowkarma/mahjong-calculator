# Task 01503: Automated Validation Pipeline Setup

## Objective
Establish a robust automated Continuous Integration (CI) pipeline to ensure the stability of the `app` module and the integrity of the `ml-pipeline` data formats, serving as a final quality gate before commencing ML training (Phase 2).

## Scope
- Files: `.github/workflows/...` (or equivalent CI configuration files), `ml-pipeline/scripts/...`.
- Constraint: Do NOT initiate any model training (Phase 2) or modify core app logic. Focus entirely on DevOps and validation automation.

## Tasks
1. **App Unit Test Automation:** Create a CI workflow that automatically triggers on pull requests or commits to the `main` branch. This workflow must execute `./gradlew test` (or the specific KMP test command) to validate the `MahjongCalculator` and all core business logic.
2. **Dataset Integrity Check Automation:** Integrate the YOLO format labeling integrity checker into the CI pipeline. The pipeline should run `python3 ml-pipeline/src/utils/check_labels.py` (or equivalent) to ensure no malformed data enters the training phase.
3. **Linter and Protocol Enforcement:** Configure the CI pipeline to execute `python3 ~/Desktop/agent-md-linter/agent_md_linter.py AGENTS.md` (adapted for CI paths) to guarantee that protocol rules are strictly followed.
4. **Build Verification:** Add a step to verify that the project compiles cleanly (`./gradlew build` and `ruff check .`).

## Definition of Done (DoD)
1. The CI workflow file (e.g., `.github/workflows/ci.yml`) is successfully created and syntactically valid.
2. The workflow includes distinct steps for KMP testing, Python linting, and dataset integrity checks.
3. All file paths and script references in the CI configuration correctly align with the project's monorepo structure as defined in `SUMMARY.xml`.
4. Code changes adhere strictly to the rules defined in `AGENTS.md` and `SUMMARY.xml`.

# 010 - Foundation & Data Synthesis: Execution Command

<!-- ### 1. [Antigravity: Gemini 3.1 Pro] Initialize Monorepo Infrastructure
- **Task**: Establish the project-wide monorepo and dependency configuration.
- **Action**:
    - **Pre-flight**: Read root `AGENTS.md` and `SUMMARY.xml` to ensure architectural compliance.
    - Configure root `settings.gradle.kts` to include `:app:composeApp`, `:app:androidApp`, and `:app:iosApp`.
    - Setup root `gradle/libs.versions.toml` with `Voyager`, `Kotlin-Inject`, and `Compose Multiplatform` versions.
    - Initialize `SUMMARY.xml` and distribute `AGENTS.md` protocols to `app/` and `ml-pipeline/`. -->

### 1. [Antigravity: Gemini 3.1 Pro] Audit & Integrate KMP Infrastructure
- **Task**: Safely integrate routing and dependency injection infrastructure into the existing KMP `app/` workspace without disrupting current features.
- **Action**:
    - **Pre-flight Audit**: Read `app/gradle/libs.versions.toml`, `app/build.gradle.kts`, and check existing UI files (e.g., `app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ui/`) to understand the current build configurations and UI architecture.
    - **Safe Integration**: Append/Update `app/gradle/libs.versions.toml` with `Voyager` and `Kotlin-Inject` versions. Apply these dependencies to `app/composeApp/build.gradle.kts` *without* overwriting existing Compose or KMP configurations.
    - **Refactoring Strategy**: Do not modify existing UI logic immediately. Instead, analyze how existing screens (like `YakuCalculationScreen`) can be migrated to Voyager, and document the strategy.
    - **State Sync**: Update the root `SUMMARY.xml` to reflect both the previously implemented core features (calculators, models) and the newly added dependency infrastructure.

---

### 2. [Jules] [Parallel Execution] Synthesis Pipeline & Scoring Engine
> [!TIP]
> Jules can execute these two scopes concurrently as they target different modules.

**[Scope A] `ml-pipeline/src/synthesis/`**
- **Action**:
    - **Pre-flight**: Read `ml-pipeline/AGENTS.md` for AI/ML development standards.
    - Implement `generate_tiles.py` to overlay 34 Mahjong tile types on random backgrounds.
    - Implement `augment_data.py` using `Albumentations` for Blur, Noise, and Perspective transforms.
    - Generate 10,000+ labeled images in YOLO format.

**[Scope B] `app/composeApp/src/commonMain/kotlin/.../calculator/`**
- **Action**:
    - **Pre-flight**: Read `app/AGENTS.md` for KMP and Voyager coding conventions.
    - Implement `MahjongCalculator` with 14-tile Agari (winning hand) detection.
    - Implement scoring for all major Japanese Yaku and Fu calculations in Korean.
    - Verify with unit tests for standard winning hands.

---

### 3. [Antigravity: Claude Opus 4.6] Logic Validation & String Mapping
- **Task**: Refine scoring edge cases and localized metadata.
- **Action**:
    - **Pre-flight**: Review root `AGENTS.md` and `app/AGENTS.md` for logic-layer protocols.
    - Review `MahjongCalculator` for Furiten, Pinfu Fu calculation, and complex wait patterns.
    - Map all Yaku names and descriptions to the `YakuData.kt` model using Korean strings.

---

### 4. [Gemini CLI] Environment Validation & Cleanup
- **Task**: Final structure check and boilerplate removal.
- **Action**:
    - **Pre-flight**: Verify `SUMMARY.xml` is up-to-date.
    - Execute `ls -R` to verify the hierarchy matches `SUMMARY.xml`.
    - Remove redundant boilerplate files from IDE templates via `rm -rf`.
    - Run `./gradlew build` to confirm zero dependency conflicts.

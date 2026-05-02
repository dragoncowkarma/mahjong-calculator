# 010 - Foundation & Data Synthesis: Execution Command

### 1. [Antigravity: Gemini 3.1 Pro] Initialize Monorepo Infrastructure
- **Task**: Establish the project-wide monorepo and dependency configuration.
- **Action**:
    - Configure root `settings.gradle.kts` to include `:app:composeApp`, `:app:androidApp`, and `:app:iosApp`.
    - Setup root `gradle/libs.versions.toml` with `Voyager`, `Kotlin-Inject`, and `Compose Multiplatform` versions.
    - Initialize `SUMMARY.xml` and distribute `AGENTS.md` protocols to `app/` and `ml-pipeline/`.

---

### 2. [Jules] [Parallel Execution] Synthesis Pipeline & Scoring Engine
> [!TIP]
> Jules can execute these two scopes concurrently as they target different modules.

**[Scope A] `ml-pipeline/src/synthesis/`**
- **Action**:
    - Implement `generate_tiles.py` to overlay 34 Mahjong tile types on random backgrounds.
    - Implement `augment_data.py` using `Albumentations` for Blur, Noise, and Perspective transforms.
    - Generate 10,000+ labeled images in YOLO format.

**[Scope B] `app/composeApp/src/commonMain/kotlin/.../calculator/`**
- **Action**:
    - Implement `MahjongCalculator` with 14-tile Agari (winning hand) detection.
    - Implement scoring for all major Japanese Yaku and Fu calculations in Korean.
    - Verify with unit tests for standard winning hands.

---

### 3. [Antigravity: Claude Opus 4.6] Logic Validation & String Mapping
- **Task**: Refine scoring edge cases and localized metadata.
- **Action**:
    - Review `MahjongCalculator` for Furiten, Pinfu Fu calculation, and complex wait patterns.
    - Map all Yaku names and descriptions to the `YakuData.kt` model using Korean strings.

---

### 4. [Gemini CLI] Environment Validation & Cleanup
- **Task**: Final structure check and boilerplate removal.
- **Action**:
    - Execute `ls -R` to verify the hierarchy matches `SUMMARY.xml`.
    - Remove redundant boilerplate files from IDE templates via `rm -rf`.
    - Run `./gradlew build` to confirm zero dependency conflicts.

<!-- ### 2. [Jules] [Parallel Execution] Synthesis Pipeline & Scoring Engine
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
    - Verify with unit tests for standard winning hands. -->

### 2. [Jules] [Parallel Execution] Synthesis Pipeline Enhancement & Calculator Audit
> [!TIP]
> Jules can execute these two scopes concurrently as they target different modules.

**[Scope A] `ml-pipeline/src/synthesis/`**
- **Action**:
    - **Pre-flight**: Read `ml-pipeline/AGENTS.md` for AI/ML development standards.
    - **Refactor & Enhance**: Review the existing `tile_synthesizer.py`. Update it (or create helper scripts like `augment_data.py`) to properly utilize `Albumentations` for Blur, Noise, and Perspective transforms.
    - **Execution**: Generate a sample batch (e.g., 100 images) in YOLO format to verify the pipeline. (Do not generate 10,000 images yet to save processing time).

**[Scope B] `app/composeApp/src/commonMain/kotlin/.../calculator/`**
- **Action**:
    - **Pre-flight Audit**: Read `app/AGENTS.md` and thoroughly inspect the ALREADY EXISTING `MahjongCalculator.kt`, `YakuCalculator.kt`, and `FuCalculator.kt`. 
    - **Validation & Localization**: DO NOT overwrite existing logic. Verify that the 14-tile Agari detection works correctly. Ensure all Japanese Yaku strings/outputs are properly localized in Korean.
    - **Test Coverage**: Run the existing unit tests in `commonTest/.../calculator/`. Add missing test cases for edge-case Yaku if necessary.

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

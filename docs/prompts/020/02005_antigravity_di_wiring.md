# 🎯 System Role
You are a Senior KMP Architect specializing in compile-time Dependency Injection, module wiring, and lifecycle management.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app (commonMain/di + platform DI)
- Milestone: 02005
- Prerequisites: 02002 completed (models integrated). Can run in parallel with 02003/02004.
- Reference: `docs/specs/08_voyager_migration_strategy.md` (Voyager + Kotlin-Inject convention)
</context>

# 🛠️ Task
<task>
1. **Pre-flight**: Review existing `AppComponent.kt` and platform-specific DI implementations.
2. **Module Setup**: Enhance `AppComponent` using `Kotlin-Inject` to manage lifetimes of:
   - `MahjongDetector` (platform-specific via expect/actual)
   - `MahjongCalculator` (shared, stateless)
   - All `ScreenModel` instances
3. **Scope Management**: Ensure `MahjongDetector` has appropriate lifecycle (singleton vs scoped).
4. **Platform Wiring**: Verify platform-specific dependencies (Android: TFLite runtime, iOS: CoreML runtime) are correctly injected via `expect/actual` modules.
5. **Convention Compliance**: Ensure all new wiring follows `docs/specs/08` Voyager convention.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] Avoid singleton leaks — MahjongDetector must be properly scoped to app lifecycle.
3. Follow Kotlin-Inject @Component/@Provides patterns exclusively (no manual instantiation).
4. DO NOT introduce any DI framework other than Kotlin-Inject.
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/AppComponent.kt
app/composeApp/src/androidMain/ (AndroidAppComponent)
app/composeApp/src/iosMain/ (IosAppComponent)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Dependency graph analysis (MahjongDetector → platform runtime)
- Scope management strategy (singleton vs per-screen)
- Kotlin-Inject integration pattern verification
</thinking>

<implementation>
- Updated DI modules and component wiring code
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] No singleton leaks (lifecycle-aware scoping)
- [ ] All ScreenModels accessible via rememberScreenModel
- [ ] EOF empty line completed
</verification>
</output_format>

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

# 🛑 Exit Criteria (명확한 종료 및 검증 조건)
에이전트는 다음 산출물이 확보되는 즉시 작업을 종료하며, 지시하지 않은 아키텍처 변경을 수행하지 않습니다.
1. **DI Graph Completion**: `AppComponent` 및 각 플랫폼별 DI 모듈의 `Kotlin-Inject` 어노테이션 기반 정의 완료.
2. **Dependency Resolution**: `MahjongDetector` 및 `MahjongCalculator`가 빌드 타임에 정상적으로 주입됨을 확인.
3. **Convention Match**: 모든 `ScreenModel`이 Voyager의 `rememberScreenModel`과 호환되도록 주입 로직이 설계됨을 확인.
4. **Leak-free Scope**: 에이전트 도구 또는 단위 테스트를 통해 싱글톤 객체의 수명 주기가 앱 수명 주기와 일치함을 검증.

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
- [ ] Exit Criteria: DI graph for AppComponent and platform modules completed
- [ ] MahjongDetector and ScreenModel injection verified
- [ ] Convention compliance (Voyager/Kotlin-Inject) and scoping verified
- [ ] EOF empty line completed
</verification>
</output_format>

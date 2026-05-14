# 08. Voyager 아키텍처 규약 (Convention)

## 개요 (Overview)
이 문서는 **Voyager** 라우팅 프레임워크 및 **Kotlin-Inject** DI 프레임워크의 사용 규약을 정의합니다. 초기 마이그레이션이 완료된 상태이며, 이후 모든 신규 화면은 이 규약을 엄격히 준수해야 합니다.

## 1. 마이그레이션 상태 (Migration Status)
`Voyager` 및 `Kotlin-Inject`를 위한 핵심 인프라는 기존 KMP 설정을 유지하며 `libs.versions.toml` 및 `app/composeApp/build.gradle.kts`에 성공적으로 통합되었습니다.

`YakuCalculationScreen`을 포함한 기존 화면들은 이미 이 구조에 맞춰 아키텍처 조정이 완료된 상태입니다.

## 2. UI 로직 규약 (Convention)

### 화면 구현 (Screen Implementation)
- 각각의 독립적인 UI 흐름은 Voyager의 `Screen` 인터페이스를 구현해야 합니다.
- Compose 컴포넌트는 재정의된 `@Composable override fun Content()` 블록 내에 래핑됩니다.
- 예시: `class YakuCalculationScreen : Screen { ... }`

### ScreenModel을 통한 상태 관리
- 비즈니스 로직과 상태는 `Screen`에서 분리되어 `ScreenModel`로 이동해야 합니다.
- 상태는 `StateFlow`를 통해 노출되며, UI에서 `collectAsState()`를 사용하여 소비됩니다.
- 예시: `class YakuCalculationScreenModel @Inject constructor() : ScreenModel { ... }`

### 의존성 주입 통합 (DI Integration)
- **Kotlin-Inject**가 `ScreenModel`의 인스턴스화를 담당합니다.
- 화면은 앱의 루트 컴포넌트를 활용하여 `rememberScreenModel`을 통해 해당 모델에 접근합니다.
- 예시:
  ```kotlin
  val component = LocalAppComponent.current
  val screenModel = rememberScreenModel { component.yakuCalculationScreenModel }
  ```

### 내비게이션 실행 (Navigation Execution)
- 내비게이션은 Voyager의 `LocalNavigator.currentOrThrow`에 의존합니다.
- `navigator.push()` 및 `navigator.pop()`과 같은 작업을 통해 화면 전환을 제어하며, 백스택(Back-stack)이 네이티브 플랫폼 구현으로부터 독립적으로 유지되도록 보장합니다.

## 3. 결론
초기 마이그레이션이 완료되었습니다. 향후 `app/` 모듈에 추가되는 모든 새로운 화면은 `AGENTS.md`에 명시된 대로 이 `Screen` 및 `ScreenModel` 규약을 준수해야 합니다.

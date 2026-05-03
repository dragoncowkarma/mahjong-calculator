# 06. 기술 아키텍처 및 제약 사항

## 1. 제로 파편화 정책 (Zero-Fragmentation Policy)

이 프로젝트는 `AGENTS.md`에 정의된 **제로 파편화 프로토콜**을 엄격히 준수합니다.

- **commonMain**: 코드의 99.9% (로직, 모델, UI, 내비게이션)를 포함합니다.
- **플랫폼 추상화**: 카메라 API 및 ML 추론과 같은 하드웨어 종속적인 기능만 인터페이스 및 DI(의존성 주입)를 통해 분리합니다.

## 2. 모듈 경계 (Monorepo)

| 모듈 | 위치 | 기술 스택 | 책임 |
| :--- | :--- | :--- | :--- |
| **공유 코어 (Shared Core)** | `app/composeApp/commonMain` | Kotlin | 공통 데이터 모델 (Tile, Hand 등) 정의 |
| **애플리케이션 모듈 (App Module)** | `app/` | KMP (Voyager, Kotlin-Inject) | UI, 점수 계산 로직, 카메라 통합 |
| **ML 모듈 (ML Module)** | `ml-pipeline/` | Python (PyTorch, YOLO) | 데이터 합성, 모델 학습 및 내보내기 |

### 2.1. 의존성 규칙
- **App/ML -> Shared Core**: 두 모듈 모두 동일한 도메인 로직(Shared Core)을 상속해야 합니다.
- **App <-> ML**: 직접적인 의존성 없음. ML 모듈은 결과물(`.tflite`, `.mlmodel`)을 생성하고, App 모듈은 이를 리소스로 소비합니다.

## 3. 시스템 제약 사항 (권장되지 않는 사항)

AI 에이전트는 아키텍처의 부패를 방지하기 위해 다음 규칙을 준수해야 합니다.

- **제약 1 (플랫폼 로직 금지)**: 비즈니스 로직을 `androidMain`이나 `iosMain`에 구현하지 마십시오. 하드웨어 전용 호출에만 `expect/actual`을 사용하십시오.
- **제약 2 (ML 파이프라인 내 UI 금지)**: `ml-pipeline`은 헤드리스(Headless) 데이터/학습 모듈로 유지되어야 합니다. GUI 프레임워크(Tkinter, Qt 등) 사용은 허용되지 않습니다.
- **제약 3 (외부 계산기 사용 금지)**: 모든 점수 계산은 `Shared Core` 로직 내에서 이루어져야 합니다. 승인되지 않은 외부 라이브러리 사용을 금지합니다.
- **제약 4 (상태 비저장 엔진)**: 계산 엔진은 영구 상태(DB, Preference 등)에 의존해서는 안 됩니다. `(Hand, GameContext) -> Result` 형태의 순수 함수여야 합니다.
- **제약 5 (한국어 지역화)**: 사용자에게 노출되는 모든 역(Yaku) 이름과 UI 문자열은 **한국어**로 작성되어야 합니다.

## 4. 기술 스택 (Tech Stack)

- **UI**: Compose Multiplatform
- **내비게이션**: Voyager (Screen/ScreenModel)
- **DI**: Kotlin-Inject (KSP)
- **ML**: YOLOv8/v11 Nano -> TFLite / CoreML
- **데이터 증강**: PyTorch, Albumentations (Blur, Noise, Perspective)

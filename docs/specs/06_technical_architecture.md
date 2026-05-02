# 06. Technical Architecture

## 1. Zero-Fragmentation Policy (AGENTS.md)
본 프로젝트는 `AGENTS.md`의 지침을 엄격히 준수하여 플랫폼 간 파편화를 최소화합니다.
- **commonMain**: 비즈니스 로직, 데이터 모델, UI 컴포넌트, 네비게이션을 99.9% 포함.
- **Platform Abstraction**: 카메라 API 및 ML 추론(Inference) 엔진과 같이 플랫폼 의존적인 기능은 Interface를 통해 추상화하고 DI로 주입.

## 2. Tech Stack
- **UI Framework**: Compose Multiplatform
- **Navigation**: Voyager
- **Dependency Injection**: Kotlin-Inject
- **Asynchronous**: Coroutines & Flow
- **ML Inference**: 
    - **Android**: TensorFlow Lite (ML Kit)
    - **iOS**: CoreML (Vision Framework)
- **Model Architecture**: YOLOv8/v11 Nano
- **AI Pipeline (Python)**:
    - **Core**: PyTorch, Ultralytics (YOLO)
    - **Data**: OpenCV, Albumentations, NumPy
    - **Export**: CoreMLTools, TFLite-Support
- **Local Storage**: Multiplatform Settings

## 3. Modular Structure (Monorepo)
```mermaid
graph TD
    Root[Project Root] --> App[app/]
    Root --> ML[ml-pipeline/]
    
    subgraph "Mobile Application (app/)"
        A[composeApp:commonMain] --> B[UI: Screens & Components]
        A --> C[Domain: Scoring Logic]
        A --> D[Data: Repository & Settings]
        A --> H[Interface: ImageAnalyzer]
        E[androidApp] --> A
        F[iosApp] --> A
    end
    
    subgraph "AI Pipeline (ml-pipeline/)"
        ML --> S[src/synthesis]
        ML --> T[src/training]
        ML --> EX[src/export]
        ML --> MD[models/]
    end
    
    EX -- deploy --> H
```

## 4. Monorepo Strategy
본 프로젝트는 단일 리포지토리에서 앱 개발과 AI 연구를 병행하는 모노레포 구조를 채택합니다.
- **app/**: Kotlin Multiplatform 기반 모바일 애플리케이션 (Zero-Fragmentation 준수).
- **ml-pipeline/**: Python 기반 AI 모델 학습 및 데이터 합성 파이프라인.

## 5. AI Development Pipeline
1. **Data Synthesis**: `src/synthesis`를 통해 34종 마작 패에 대한 대량의 합성 이미지 생성.
2. **Model Training**: `src/training`에서 YOLOv8/v11 Nano 아키텍처를 사용하여 객체 탐지 모델 학습.
3. **Export & Optimization**: `src/export`를 통해 모바일 환경에 최적화된 TFLite 및 CoreML 모델 추출.

## 6. Coding Standards
- **Brace Style**: K&R style (opening brace on the same line).
- **Newline**: 모든 파일의 끝에는 하나의 빈 줄(Empty newline)을 유지.
- **Naming**: Kotlin 표준 코딩 컨벤션 및 Voyager Screen 네이밍 규칙 준수.

# Mahjong Calculator (마작 점수 계산기) 🀄️

마작의 복잡한 점수 계산을 단 한 번의 촬영으로 해결하세요. 이 프로젝트는 인공지능 기반의 패 인식 기술과 크로스 플랫폼 모바일 개발 기술이 결합된 모노레포(Monorepo) 프로젝트입니다.

## 📁 프로젝트 구조

본 프로젝트는 다음과 같은 두 가지 핵심 영역으로 나뉩니다.

### 1. [App (Mobile application)](./app/)
- **기술 스택**: Kotlin Multiplatform (KMP), Compose Multiplatform, Voyager, Kotlin-Inject.
- **역할**: 사용자 인터페이스 제공, 오프라인 ML 추론, 마작 점수 계산 로직 수행.
- **대상**: Android 및 iOS.

### 2. [ML Pipeline (AI Development)](./ml-pipeline/)
- **기술 스택**: Python, YOLOv8/v11, TFLite, CoreML.
- **역할**: 마작 패 인식을 위한 객체 탐지 모델 학습, 합성 데이터 생성, 모바일 최적화 모델 내보내기.

## 🛠 주요 기능
- **실시간 오프라인 패 인식**: 기기 내에서 직접 작동하는 YOLO 기반 인식 엔진.
- **정확한 점수 산출**: 리이치 마작 표준 규칙 준수 (역, 부수, 점수 계산).
- **크로스 플랫폼**: Android와 iOS에서 동일한 사용자 경험 제공.

## 📜 기획 및 문서
상세한 기획 및 기술 명세는 [docs/specs/](./docs/specs/) 디렉토리에서 확인할 수 있습니다.

---
> 이 프로젝트는 마작을 사랑하는 모든 이들이 더 즐겁게 게임을 즐길 수 있도록 지속적으로 발전하고 있습니다.
# 03. Application Logic

## 1. Recognition Engine (인식 엔진)
디바이스별 최적화된 ML 라이브러리를 사용하여 오프라인 패 인식을 수행합니다.
- **Model**: YOLOv8/v11 Nano 기반의 경량화된 객체 탐지 모델.
- **Android**: Google ML Kit 및 **TensorFlow Lite (.tflite)** 라이브러리를 활용한 가속화.
- **iOS**: Apple Vision Framework 및 **CoreML (.mlmodel)**을 활용한 고성능 하드웨어 가속.
- **Interface**: KMP의 `expect`/`actual` 패턴을 사용하여 `ImageAnalyzer` 인터페이스를 각 플랫폼에서 구현.

## 2. Scoring Engine (점수 계산 로직)
리이치 마작 표준 규칙을 준수하는 계산 엔진입니다.
- **Yaku Determination**: 완성된 패를 분석하여 적용 가능한 모든 역을 판별.
- **Fu Calculation**: 머리와 몸통의 구성, 대기 형태, 울음 여부, 쯔모/론 여부에 따른 부수 계산.
- **Point Calculation**: 판수(Han)와 부수(Fu)를 결합하여 최종 점수 산출.
- **Payment Distribution**: 쯔모 시 각 플레이어가 지불해야 할 점수(오야/코 구분) 및 론 시 방총자가 지불할 점수 산출.

## 3. State Management (상태 관리 시스템)
Voyager 프레임워크의 `ScreenModel`을 사용하여 데이터의 일관성을 유지합니다.
- **RecognitionState**: 카메라로부터 들어오는 실시간 데이터 스트림 및 바운딩 박스 정보 관리.
- **CorrectionState**: 사용자가 편집 중인 임시 패 데이터 관리.
- **CalculationState**: 최종 결정된 패와 상황 변수를 기반으로 산출된 결과 데이터 저장.

## 4. Data Persistence (데이터 보존 정책)
Multiplatform Settings를 활용하여 사용자의 환경 설정 및 최근 계산 기록을 저장합니다.
- **User Settings**: 선호하는 언어, 도라 표시 방식(표지패 vs 도라패) 등.
- **History (v2.0)**: 과거 계산 기록을 로컬에 저장하여 복기 용도로 활용.

# Mahjong Tiles ML Pipeline

이 디렉토리는 마작 패 인식을 위한 AI 모델(YOLO)의 데이터 생성 및 학습을 담당합니다.

## 디렉토리 구조
- `data/`: 학습 및 검증 데이터셋
    - `raw/`: 원본 타일 이미지 및 배경 이미지
    - `synthetic/`: 생성된 합성 데이터셋
    - `processed/`: 학습을 위해 전처리된 데이터
- `models/`: 학습 완료된 모델 파일 (.pt, .tflite, .mlmodel)
- `src/`: 핵심 스크립트
    - `synthesis/`: 합성 데이터 생성 로직
    - `training/`: YOLO 모델 학습 스크립트
    - `export/`: 모바일 플랫폼용 모델 변환 스크립트

## 시작하기
1. Python 가상환경 생성 및 활성화:
   ```bash
   python -m venv venv
   source venv/bin/activate  # macOS/Linux
   ```
2. 의존성 설치:
   ```bash
   pip install -r requirements.txt
   ```

## 워크플로우
1. `src/synthesis/` 스크립트를 사용하여 대량의 합성 데이터 생성.
2. `src/training/` 스크립트로 YOLOv8/v11 Nano 모델 학습.
3. `src/export/` 스크립트로 Android(TFLite) 및 iOS(CoreML)용 모델 추출.

# 📋 모델 익스포트 문제 해결 및 검증 보고서 (Milestone 02002)

## 1. 개요
- **목적**: 학습된 YOLOv8n 모델을 모바일 기기(Android, iOS)에서 사용 가능한 포맷으로 변환 및 검증.
- **대상**: `ml-pipeline/models/mahjong_yolo_nano/weights/best.pt`
- **목표**: 
    - ONNX, CoreML, TFLite(FP16) 변환.
    - 모델 크기 10MB 이하 유지.
    - 원본 모델 대비 오차율 1% 이내 보장.

## 2. 결과 요약
| 포맷 | 상태 | 크기 | 오차율 (Parity) | 비고 |
| :--- | :--- | :--- | :--- | :--- |
| **ONNX** | ✅ 성공 | 11.8 MB | - | 중간 단계 포맷 |
| **CoreML** | ✅ 성공 | 5.9 MB | **0.00%** | iOS 통합 완료 |
| **TFLite** | ❌ 실패 | - | - | 환경/라이브러리 버그로 인한 차단 |

---

## 3. 주요 문제 및 해결 과정

### 3.1. CoreML 변환 및 검증 (성공)
- **과정**: `ultralytics` 라이브러리를 통해 CoreML (`.mlpackage`)로 직접 익스포트 수행.
- **해결**: 초기 추론 시 이미지 크기 불일치(640 vs 320)로 인한 에러가 발생했으나, `imgsz=320`을 명시적으로 지정하여 해결.
- **검증**: PyTorch 원본 모델과 CoreML 모델 간의 Confidence Score 비교 결과, **오차율 0%**로 완벽한 정밀도 유지 확인.

### 3.2. TFLite 변환 실패 (Blocker)
TFLite 변환 도구인 `onnx2tf`와 현재 개발 환경(Python 3.12, TF 2.16) 간의 호환성 문제로 변환이 중단되었습니다.

#### 발생한 오류:
1. **MaxPool 패딩 계산 오류 (Opset 11~17)**:
   - `TypeError: unsupported operand type(s) for -: 'NoneType' and 'int'`
   - `onnx2tf` 내부에서 `MaxPool` 레이어의 차원을 해석하는 과정에서 발생하는 라이브러리 고유 버그로 판단됨.
2. **Concat 레이어 차원 불일치 (Opset 10)**:
   - `ValueError: Dimension 0 in both shapes must be equal, but are 2 and 2100`
   - Opset을 낮추어 패딩 오류는 우회했으나, YOLOv8 헤드 부분의 `Concat` 레이어에서 텐서 크기 미스매치 발생.

#### 시도된 해결책 (효과 없음):
- ONNX Opset 변경 (10, 11, 12, 13, 17).
- `onnxsim` (Simplifier) 사용 여부 전환 및 수동 설치.
- `keras=True` 플래그 사용 및 `dynamic=False` 설정.
- `onnx2tf` 직접 호출 및 입력 텐서 크기 고정 (`-ois`).

---

## 4. 향후 조치 및 제언 (Technical Debt)
- **Android 대응**: 현재 환경에서는 TFLite 변환이 불가능하므로, 안정적인 `onnx2tf` 버전을 지원하는 별도의 가상 환경(Python 3.10 권장) 또는 Google Colab 등에서 변환 후 모델 파일을 수동으로 병합해야 함.
- **모델 버전 관리**: `mahjong_detector_v1.0` 체계를 수립하였으며, 추후 TFLite 모델 확보 시 동일한 경로(`app/composeApp/src/commonMain/composeResources/models/`)에 배치 필요.

## 5. 최종 산출물 위치
- **통합 스크립트**: `ml-pipeline/src/export/convert_and_verify.py`
- **iOS 모델**: `app/composeApp/src/commonMain/composeResources/models/mahjong_detector_v1.0.mlpackage`
- **공통 모델**: `app/composeApp/src/commonMain/composeResources/models/mahjong_detector_v1.0.onnx`

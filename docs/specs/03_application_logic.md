# 03. 애플리케이션 로직 및 공유 코어 (Shared Core)

## 1. 공유 코어 엔티티 (Shared Core Entities)
일관성을 유지하기 위해 모든 모듈에서 공유되는 도메인 데이터 모델입니다. AI 에이전트는 이 구조를 엄격히 준수해야 합니다.

| 엔티티 | 설명 | 제약 조건 |
| :--- | :--- | :--- |
| **Tile (패)** | 개별 마작 패. | 34종 + 적오(Red Five) 여부. |
| **Meld (울음)** | 치, 퐁, 깡 (공개/비공개). | `type` (Enum), `tiles` (List), `isOpen` (Boolean). |
| **Hand (패 구성)** | 플레이어가 보유한 전체 패. | 13/14개의 패 + 울음 목록. |
| **GameContext (상황)** | 점수 계산을 위한 환경 변수. | `roundWind`, `seatWind`, `doraIndicators`, `isRiichi`, `isTsumo`, `isDealer`. |

## 2. 인식 엔진 (Recognition Engine)
플랫폼별 하드웨어 가속을 사용하여 오프라인 패 탐지를 수행합니다.

| 플랫폼 | 라이브러리 | 모델 포맷 |
| :--- | :--- | :--- |
| **Android** | Google ML Kit (TFLite) | `.tflite` |
| **iOS** | Apple Vision (CoreML) | `.mlmodel` |

- **제약 조건**: 상대적인 바운딩 박스 좌표를 포함한 `Tile` 객체 리스트를 반환해야 합니다.

## 3. 계산 엔진 (Scoring Engine)
`Hand`와 `GameContext`를 기반으로 역(Yaku), 부수(Fu), 점수를 계산합니다.

### 3.1. 계산 흐름
1. **아기리 판정 (Agari Detection)**: 보유한 패가 유효한 화료 형태(14패)인지 확인합니다.
2. **역 판정 (Yaku Determination)**: 유효한 모든 역(예: 리이치, 탕야오)을 식별합니다.
3. **부수 계산 (Fu Calculation)**: 기본 부수(20/30) 및 조정 부수(대기, 울음 등)를 계산합니다.
4. **점수 매핑 (Point Mapping)**: 친(Dealer) 여부에 따라 번수(Han)와 부수를 최종 점수로 매핑합니다.

### 3.2. 입출력 예시
**입력 (JSON 형태):**
```json
{
  "hand": ["1m", "2m", "3m", "4s", "5s", "6s", "7p", "8p", "9p", "E", "E", "E", "W", "W"],
  "context": { "roundWind": "E", "seatWind": "E", "isTsumo": true }
}
```
**출력 (JSON 형태):**
```json
{
  "yaku": ["Tanyao", "Pingfu"],
  "han": 2, "fu": 20,
  "score": { "dealerPay": 700, "nonDealerPay": 700, "total": 2100 }
}
```

## 4. 상태 관리 (Voyager ScreenModel)
- **RecognitionState**: 카메라 스트림 및 현재 탐지 오버레이를 관리합니다.
- **CorrectionState**: 탐지가 부정확할 경우 수동 패 편집을 처리합니다.
- **CalculationState**: 최종 확인된 패와 결과 점수 데이터를 보유합니다.

# 03. Application Logic & Shared Core

## 1. Shared Core Entities (Domain Data Model)

These entities are shared across all modules to ensure consistency. AI agents must strictly follow these structures.

| Entity | Description | Constraints |
| :--- | :--- | :--- |
| **Tile** | Individual Mahjong tile. | 34 types + Red Five (Boolean). |
| **Meld** | Chow, Pong, or Kong (Open/Closed). | `type` (Enum), `tiles` (List), `isOpen` (Boolean). |
| **Hand** | Total tiles held by a player. | 13/14 Tiles + List of Melds. |
| **GameContext** | Environment for scoring. | `roundWind`, `seatWind`, `doraIndicators`, `isRiichi`, `isTsumo`, `isDealer`. |

## 2. Recognition Engine (Stateless Inference)

Performs offline tile detection using platform-specific hardware acceleration.

| Platform | Library | Model Format |
| :--- | :--- | :--- |
| **Android** | Google ML Kit (TFLite) | `.tflite` |
| **iOS** | Apple Vision (CoreML) | `.mlmodel` |

- **Constraint**: Must return a list of `Tile` objects with relative bounding box coordinates.

## 3. Scoring Engine (Stateless Calculation)

Calculates Yaku, Fu, and Score based on a `Hand` and `GameContext`.

### 3.1. Calculation Flow
1. **Agari Detection**: Verify if the hand is a valid winning hand (14 tiles).
2. **Yaku Determination**: Identify all valid Yaku (e.g., Riichi, Tanyao).
3. **Fu Calculation**: Calculate base Fu (20/30) and adjustments (e.g., waits, melds).
4. **Point Mapping**: Map Han and Fu to final points based on dealer status.

### 3.2. Example I/O
**Input (JSON-like):**
```json
{
  "hand": ["1m", "2m", "3m", "4s", "5s", "6s", "7p", "8p", "9p", "E", "E", "E", "W", "W"],
  "context": { "roundWind": "E", "seatWind": "E", "isTsumo": true }
}
```
**Output (JSON-like):**
```json
{
  "yaku": ["Tanyao", "Pingfu"],
  "han": 2, "fu": 20,
  "score": { "dealerPay": 700, "nonDealerPay": 700, "total": 2100 }
}
```

## 4. State Management (Voyager ScreenModel)

- **RecognitionState**: Manages the camera stream and current detection overlay.
- **CorrectionState**: Handles manual tile editing if detection is inaccurate.
- **CalculationState**: Holds the final verified hand and the resulting score data.

---


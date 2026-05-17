# 🎯 System Role
You are a Senior KMP Architect responsible for cross-platform ML inference interfaces, post-processing pipelines, and bounding box normalization.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP)
- Module: app/commonMain/ml
- Milestone: 02003
- Prerequisites: 02002 completed (TFLite/CoreML models exported and integrated)
- Reference: `docs/specs/03_application_logic.md` (34 classes, Red Five post-processing), `docs/specs/06_technical_architecture.md` (inference < 200ms)
</context>

# 🛠️ Task
<task>
1. **Pre-flight**: Review `app/AGENTS.md` and existing `TileDetectionModel` interface.
2. **Interface Definition**: Define the `MahjongDetector` interface with platform-specific `expect/actual` declarations for TFLite (Android) and CoreML (iOS) runtimes.
3. **Post-Processing (commonMain)**: Implement NMS (Non-Maximum Suppression) and bounding box decoding in `commonMain` to ensure consistency across platforms.
4. **Red Five Detection**: Implement color-based post-processing for Red Five (적오) identification after initial 34-class detection.
5. **Coordinate Mapping**: Implement normalized coordinate mapper (inference coords → display coords).
6. **Validation**: Validate inference results using sample images against known ground truth.
</task>

# 🛑 Exit Criteria (명확한 종료 및 검증 조건)
에이전트는 다음 산출물이 확보되는 즉시 작업을 종료하며, 추가적인 세션이나 지시하지 않은 리팩토링을 수행하지 않습니다.
1. **Interface & Implementation**: `MahjongDetector` 인터페이스 및 Android/iOS별 `actual` 구현체 코드 작성 완료.
2. **Post-processing Engine**: `commonMain` 내 NMS 및 적오(Red Five) 판별 로직 작동 확인.
3. **Performance SLA**: 단일 프레임 추론 및 후처리 합산 시간이 200ms 이하임을 로그로 확인.
4. **Coordinate Accuracy**: 정규화된 좌표가 타겟 디스플레이 좌표로 정확히 변환됨을 단위 테스트로 확인.

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [CRITICAL] Maintain inference latency < 200ms per frame (SLA requirement).
3. All post-processing logic MUST reside in `commonMain` — no platform-specific post-processing.
4. Red Five is NOT a separate ML class — use color analysis on detected "5m", "5p", "5s" tiles.
</constraints>

# 💻 Input
<input_data>
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/ml/
app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/models/TileDetectionModel.kt
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- expect/actual design pattern analysis for ML runtime abstraction
- NMS algorithm selection and configuration
- Red Five color analysis approach
</thinking>

<implementation>
- Interface definitions, NMS implementation, coordinate mapper
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Exit Criteria: MahjongDetector interface & platform implementations completed
- [ ] Performance SLA (inference < 200ms) and NMS/Red Five logic verified
- [ ] Coordinate mapping unit tests passed
- [ ] EOF empty line completed
</verification>
</output_format>

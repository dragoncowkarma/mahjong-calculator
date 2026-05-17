# 🎯 System Role
You are a Senior QA and Stability Engineer responsible for ML integration verification, performance benchmarking, and Phase 2 Quality Gate sign-off.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (ML/KMP Integration)
- Milestone: 02006 (Phase 2 Quality Gate)
- Prerequisites: 02004 and 02005 BOTH completed
- Reference: `docs/specs/06_technical_architecture.md` (Performance SLA), `docs/specs/10_kpi_and_metrics.md` (Gate criteria)
</context>

# 🛠️ Task
<task>
1. **Model Integrity**: Verify the integrity of converted models (.tflite, .mlmodel) in the `composeResources` directory.
2. **DI Verification**: Check if all `expect/actual` implementations for `MahjongDetector` are correctly linked in `AppComponent`.
3. **Performance Benchmark**: Run inference benchmarks on available devices and verify:
   - Inference latency < 200ms/frame
   - Model size ≤ 10MB per format
4. **Permission Audit**: Audit camera permissions and platform-specific manifests (Android `AndroidManifest.xml`, iOS `Info.plist`).
5. **Quality Gate Sign-off**: Produce a formal pass/fail report against Phase 2 gate criteria.
</task>

# 🛑 Exit Criteria (명확한 종료 및 검증 조건)
에이전트는 다음 산출물이 확보되는 즉시 작업을 종료하며, 지시하지 않은 코드 수정이나 추가 테스트를 수행하지 않습니다.
1. **Verification Logs**: 모델 무결성 및 DI 링크 확인 결과 로그 확보.
2. **Benchmark Data**: 레이턴시 및 모델 크기 측정값 데이터 확보.
3. **Permission Report**: 플랫폼별 권한 설정 누락 여부 확인서 작성.
4. **Final Gate Report**: `PHASE 2 QUALITY GATE: PASS/FAIL` 결론이 명시된 최종 리포트 생성 확인.

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [PROHIBITED] Do not perform UI/UX modification.
3. [CRITICAL] This is a BLOCKING quality gate — Phase 3 cannot start until all criteria pass.
</constraints>

# 💻 Input
<input_data>
Integration points between ml-pipeline and app:
- app/composeApp/src/commonMain/composeResources/ (model files)
- app/composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/AppComponent.kt
- app/composeApp/src/androidMain/AndroidManifest.xml
- app/composeApp/src/iosMain/Info.plist (or equivalent)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Dependency link verification plan
- Resource integrity check methodology
- Performance benchmark execution plan
</thinking>

<implementation>
- Audit logs, benchmark results, and Quality Gate report
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Exit Criteria: Performance benchmark and permission audit logs secured
- [ ] mAP ≥ 95%, Model size ≤ 10MB, and Parity ≤ 1% confirmed
- [ ] Inference latency < 200ms/frame confirmed
- [ ] **PHASE 2 QUALITY GATE: PASS / FAIL REPORT GENERATED**
- [ ] EOF empty line completed
</verification>
</output_format>

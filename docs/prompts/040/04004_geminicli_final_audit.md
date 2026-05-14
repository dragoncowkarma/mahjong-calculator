# 🎯 System Role
You are a Senior Release Engineer and QA Lead responsible for final release verification, app store compliance, ASO optimization, and launch readiness sign-off.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (Launch)
- Milestone: 04004 (Final Launch Gate)
- Prerequisites: 04002 and 04003 completed
- Reference: `docs/specs/10_kpi_and_metrics.md` (crash rate < 0.1%), `docs/specs/09_risk_management.md` (R05 store rejection, R06 privacy)
</context>

# 🛠️ Task
<task>
1. **Full Regression Test**: Execute comprehensive regression test suite per `docs/specs/11_test_strategy.md`.
2. **Performance Audit**: Final benchmark against all SLA criteria:
   - Crash rate < 0.1%
   - Cold start < 2 seconds
   - Inference < 200ms/frame
   - 60fps UI
3. **Store Compliance**:
   - Android: `AndroidManifest.xml` permissions audit, Play Store metadata
   - iOS: `Info.plist` usage descriptions, App Store metadata
   - Privacy policy document preparation
4. **ASO Optimization**:
   - Korean market keyword research (마작 점수 계산기, 마작 계산기, 리치마작 등)
   - App store screenshots preparation (6 screenshots per platform)
   - App description writing (Korean, with keyword optimization)
5. **Multi-language Preparation**: Prepare localization framework for v2.0 (Japanese/English), verify Korean strings are complete.
6. **Release Candidate**: Build RC, perform final smoke test, sign-off.
</task>

# ⚠️ Constraints
<constraints>
1. Ensure EXACTLY one empty line at the end of every file (EOF).
2. [PROHIBITED] Do not add new features — this is a stabilization and launch phase.
3. [CRITICAL] Must address ALL risk items from `docs/specs/09_risk_management.md` R05 and R06.
4. Privacy policy MUST explicitly state: "All image data is processed on-device only. No data is transmitted to external servers."
</constraints>

# 💻 Input
<input_data>
Entire project codebase (app/ and ml-pipeline/)
docs/specs/ (all specification documents)
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Regression test plan and coverage assessment
- Store compliance checklist
- ASO keyword strategy
</thinking>

<implementation>
- Regression test results, store metadata, privacy policy, ASO assets
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Crash rate < 0.1%
- [ ] Cold start < 2 seconds
- [ ] All 42 Yaku tests pass
- [ ] Store permissions and descriptions complete
- [ ] Privacy policy prepared
- [ ] ASO keywords optimized for Korean market
- [ ] **FINAL LAUNCH GATE: PASS / FAIL**
- [ ] EOF empty line completed
</verification>
</output_format>

# 07. Development Milestones

## Phase 1: Foundation & Data Synthesis (Weeks 1-4)
- [x] **Milestone 01001**: Audit & Integrate KMP Infrastructure
    - 프로젝트 모노레포 구조 확립 및 `app/`, `ml-pipeline/` 기본 설정.
    - `SUMMARY.xml` 및 `AGENTS.md` 기반의 작업 프로토콜 초기화.
- [x] **Milestone 01002**: Synthesis Pipeline & Scoring Engine
    - 34종 마작 패에 대한 합성 데이터 생성(`ml-pipeline/src/synthesis`) 스크립트 구축.
    - `MahjongCalculator` 핵심 로직 및 14패 아가리(Agari) 판정 엔진 prototype 개발.
- [ ] **Milestone 01003**: Logic Validation
    - 주요 일본 마작 역(Yaku) 및 부수(Fu) 계산 로직 검증 및 단위 테스트.
    - 데이터 증강(Augmentation)을 통한 학습용 데이터셋 검증.
- [ ] **Milestone 01004**: Environment Validation
    - 전체 프로젝트 환경 설정 최적화 및 빌드 파이프라인 무결성 검사.

## Phase 2: AI Model Training & Optimization (Weeks 5-8)
- [ ] **Milestone 02001**: YOLO Training
    - YOLOv8/v11 Nano 기반의 객체 탐지 모델 학습 (`ml-pipeline/src/training`).
    - mAP 95% 이상의 인식률 달성을 위한 하이퍼파라미터 튜닝.
- [ ] **Milestone 02002**: Model Export
    - 모델 성능 평가 및 모바일 최적화 (`ml-pipeline/src/export` -> TFLite, CoreML).
    - FP16 양자화 적용 및 추론 속도 벤치마크 수행.
- [ ] **Milestone 02003**: Inference Interfaces
    - 플랫폼별 ML 추론 엔진(ImageAnalyzer) 인터페이스 구현 및 연동.
    - 바운딩 박스 정규화 좌표 매퍼 구현.
- [ ] **Milestone 02004**: Camera Pipeline
    - 카메라 인터페이스 및 실시간 이미지 스트림 처리 파이프라인 구현 (`app/` 모듈).
    - 프레임 회전, 스케일링 등 전처리 로직 `commonMain` 통합.
- [ ] **Milestone 02005**: DI Wiring
    - `Kotlin-Inject`를 활용한 모듈 간 의존성 주입 구조 고도화.
- [ ] **Milestone 02006**: Verification
    - ML 모델 통합 후 실시간 인식 성능 및 안정성 최종 검증.

## Phase 3: UI Integration & UX Polish (Weeks 9-12)
- [ ] **Milestone 03001**: Design System
    - TileCorrectionPanel 및 상황 설정 UI 개발 (Voyager Screen).
    - 34종 패 선택 그리드 및 다크 모드/테마 최적화.
- [ ] **Milestone 03002**: Camera Overlay
    - Compose `Canvas` 기반의 실시간 바운딩 박스 렌더링 및 오버레이 시각화.
- [ ] **Milestone 03003**: Scoring Dashboard
    - 계산된 역(Yaku) 목록 및 점수 상세 명세 대시보드 화면 고도화.
- [ ] **Milestone 03004**: Feedback Loop
    - 사용자 피드백(오인식 데이터) 수집 및 서비스 안정성 로직 구현.
- [ ] **Milestone 03005**: UI Verification
    - 기기별 Safe Area 최적화 및 UI 성능/Recomposition 벤치마크 수행.

## Phase 4: Expansion (Weeks 13+)
- [ ] **Milestone 04001**: History Tracking
    - `SQLDelight` 기반의 과거 계산 기록 저장 및 로컬 DB 연동.
- [ ] **Milestone 04002**: Statistics UI
    - 역별 통계 및 승률 분석 대시보드 UI 구현.
- [ ] **Milestone 04003**: Export & Sync
    - Shanten Assistant 프로토타입 및 데이터 내보내기/동기화 기능.
- [ ] **Milestone 04004**: Final Readiness
    - 글로벌 런칭을 위한 다국어 지원(ASO) 및 최종 릴리즈 준비.

# 07. Development Milestones

## Phase 1: Foundation & Data Synthesis (Weeks 1-4)
- **Milestone 1**: 프로젝트 모노레포 구조 확립 및 `app/`, `ml-pipeline/` 기본 설정.
    - `settings.gradle.kts` 및 `libs.versions.toml` 통합 관리 설정.
    - `SUMMARY.xml` 및 `AGENTS.md` 기반의 작업 프로토콜 초기화.
- **Milestone 2**: 34종 마작 패에 대한 합성 데이터 생성(`ml-pipeline/src/synthesis`) 스크립트 구축.
    - `generate_tiles.py`를 통한 랜덤 배경/조명 기반 이미지 합성.
    - YOLO 형식의 라벨링 자동화 스크립트 구현.
- **Milestone 3**: 점수 계산 엔진 프로토타입 개발 (KMP `commonMain` 기반).
    - `MahjongCalculator` 핵심 로직 및 14패 아가리(Agari) 판정 엔진.
    - 주요 일본 마작 역(Yaku) 및 부수(Fu) 계산 로직 구현.
- **Milestone 4**: 데이터 증강(Augmentation)을 통한 학습용 고품질 데이터셋 확보.
    - `Albumentations` 기반의 노이즈, 블러, 왜곡 변환 적용.
    - 대규모 데이터셋 생성 및 검증 파이프라인 구축.

## Phase 2: AI Model Training & Optimization (Weeks 5-8)
- **Milestone 5**: YOLOv8/v11 Nano 기반의 객체 탐지 모델 학습 (`ml-pipeline/src/training`).
    - `yolo_config.yaml` 설정 및 Nano 모델 가중치 학습.
    - mAP 95% 이상의 인식률 달성을 위한 하이퍼파라미터 튜닝.
- **Milestone 6**: 모델 성능 평가 및 모바일 최적화 (`ml-pipeline/src/export` -> TFLite, CoreML).
    - FP16 양자화 적용 및 TFLite/CoreML 변환.
    - 모바일 기기에서의 추론 속도(Latency) 벤치마크 수행.
- **Milestone 7**: 카메라 인터페이스 및 실시간 이미지 스트림 처리 파이프라인 구현 (`app/` 모듈).
    - `CameraFeedManager` 및 `ImageAnalyzer` 공통 인터페이스 구현.
    - 프레임 회전, 스케일링 등 전처리 로직 `commonMain` 통합.
- **Milestone 8**: 플랫폼별 ML 추론 엔진(ImageAnalyzer) 인터페이스 구현 및 연동.
    - `Kotlin-Inject`를 활용한 플랫폼별 디텍터 주입.
    - 바운딩 박스 정규화 좌표를 UI 좌표로 변환하는 매퍼 구현.

## Phase 3: UI Integration & UX Polish (Weeks 9-12)
- **Milestone 9**: TileCorrectionPanel 및 상황 설정 UI 개발 (Voyager Screen).
    - 34종 패 선택용 그리드 및 오인식 패 수동 수정 기능.
    - 리치, 도라 개수, 친/자 설정 등 게임 상황 입력 UI.
- **Milestone 10**: 실시간 인식 결과 시각화 (바운딩 박스/오버레이) 구현.
    - Compose `Canvas` 기반의 실시간 바운딩 박스 렌더링.
    - 인식 결과에 따른 카테고리별 색상 코드 적용.
- **Milestone 11**: 결과 대시보드 및 상세 리포트 화면 고도화.
    - 계산된 역(Yaku) 목록 및 점수 상세 명세 레이아웃.
    - 직관적인 점수 애니메이션 및 결과 공유 기능.
- **Milestone 12**: iOS/Android 플랫폼 최적화 및 사용자 테스트.
    - 기기별 Safe Area 및 제스처 최적화.
    - UI 성능 벤치마크 및 불필요한 Recomposition 제거.

## Phase 4: Expansion (Weeks 13+)
- **Milestone 13**: 서비스 베타 릴리즈 및 실데이터 기반 모델 재학습 파이프라인 가동.
    - 사용자 피드백(오인식 데이터) 수집 텔레메트리 구축.
    - 모델 자동 재학습 및 배포 파이프라인 연동.
- **Milestone 14**: 과거 계산 기록 저장 및 분석 기능 추가.
    - `SQLDelight` 기반의 로컬 데이터베이스 저장 로직.
    - 역별 통계 및 승률 분석 대시보드 구현.
- **Milestone 15**: Shanten Assistant 등 보조 유틸리티 개발.
    - 텐파이까지 남은 패 수(Shanten) 계산 엔진 구축.
    - 최적 타패 추천 기능 프로토타입.
- **Milestone 16**: 글로벌 런칭 및 앱 스토어 최적화 (ASO).
    - 한국어/일본어/영어 다국어 지원 (Compose Resources).
    - 스토어 등록 정보 및 마케팅 자산 생성.

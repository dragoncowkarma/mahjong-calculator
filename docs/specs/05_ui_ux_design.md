# 05. UI/UX Design

## 1. Primary Screens

### TileRecognitionScreen
- **Camera Viewport**: 중앙에 패를 정렬할 수 있는 가이드라인 제공.
- **Scan Button**: 현재 화면의 패를 캡처하여 인식 프로세스 시작.
- **Flashlight Toggle**: 어두운 곳에서도 인식이 가능하도록 플래시 제어.

### TileCorrectionPanel (Overlay)
- **Grid View**: 인식된 14개의 패를 그리드 형태로 표시.
- **Selection Mode**: 패를 선택하면 선택 가능한 모든 타일 리스트가 하단에 노출되어 즉시 교체 가능.

### GameStatePanel
- **Quick Selectors**: 동/남/서/북, 리이치 여부, 도라 수 등을 탭하여 빠르게 설정.
- **Toggle Switches**: 쯔모/론, 일발 등 상태 설정.

### ResultListScreen
- **Accordion Style**: 적용된 각 역의 이름과 판수를 리스트업하고, 상세 정보를 펼쳐볼 수 있는 구조.

### ScoreResultDashboard
- **Big Typography**: 최종 점수를 가장 크게 표시.
- **Distribution Table**: 정산 금액(예: 오야 12000, 코 4000/4000)을 명확히 시각화.

## 2. Interaction Design
- **Haptic Feedback**: 인식 성공, 버튼 클릭, 계산 완료 시 적절한 진동 피드백.
- **Smooth Transitions**: 화면 전환 시 Voyager의 네이티브 애니메이션을 활용하여 부드러운 사용자 경험 제공.
- **Micro-animations**: 계산 프로세스 진행 중임을 알리는 인디케이터나 점수 카운팅 효과 적용.

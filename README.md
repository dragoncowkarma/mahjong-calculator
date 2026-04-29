# Mahjong Calculator (마작 점수 계산기) 🀄️

![Mahjong Calculator Mockup](./docs/images/mockup.png)

마작의 복잡한 점수 계산을 단 한 번의 촬영으로 해결하세요. **Mahjong Calculator**는 최신 AI 이미지 인식 기술과 Kotlin Multiplatform을 결합하여, 마작 초보자부터 숙련자까지 모두가 게임의 재미에만 집중할 수 있도록 돕는 스마트 도구입니다.

## 🌟 프로젝트 비전

마작은 깊은 전략과 재미를 가진 게임이지만, 입문자들에게는 복잡한 점수 계산 체계가 큰 장벽이 되곤 합니다. 본 프로젝트는 이러한 기술적 장벽을 허물고, 누구나 쉽고 정확하게 결과를 확인할 수 있는 환경을 제공하는 것을 목표로 합니다.

## ✨ 핵심 기능

### 1. 초심자 친화적 점수 산출
- **역(Yaku) 및 부수(Fu) 자동 판정**: 패의 조합을 분석하여 적용 가능한 모든 역과 부수를 자동으로 계산합니다.
- **상세 결과 리포트**: 단순히 점수만 보여주는 것이 아니라, 어떤 역이 적용되었는지 상세한 브레이크다운을 제공하여 학습 효과를 돕습니다.

### 2. 카메라 기반 AI 패 인식
- **Computer Vision 기술 활용**: 카메라로 완성된 패를 촬영하면 AI가 실시간으로 각 타일을 인식합니다.
- **수동 입력 최소화**: 번거로운 터치 입력 없이도 빠르게 게임 결과를 기록하고 계산할 수 있습니다.

### 3. 완벽한 크로스 플랫폼 경험
- **Android & iOS 동시 지원**: Kotlin Multiplatform (KMP) 및 Compose Multiplatform을 사용하여 두 플랫폼 모두에서 네이티브에 가까운 성능과 동일한 UI/UX를 제공합니다.

## 🛠 기술 아키텍처

본 프로젝트는 유지보수성과 확장성을 위해 최신 모바일 개발 패러다임을 준수합니다.

- **언어 및 플랫폼**: [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- **UI 프레임워크**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- **상태 관리 및 네비게이션**: [Voyager](https://voyager.adriel.cafe/)
- **의존성 주입**: [Kotlin-Inject](https://github.com/evant/kotlin-inject)
- **비동기 처리**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **로컬 데이터 저장**: [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)

---

> 이 프로젝트는 마작을 사랑하는 모든 이들이 더 즐겁게 게임을 즐길 수 있도록 지속적으로 발전하고 있습니다.
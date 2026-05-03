# 01. Product Requirements Document (PRD): Mahjong Calculator

This document defines the core specifications for the Mahjong Calculator multi-module system. It serves as the primary context for AI coding agents to ensure architectural consistency and prevent logic fragmentation.

## 1. Project Overview & Strategic Alignment

| 항목 (Field) | 상세 설명 (Details) |
| :--- | :--- |
| **Project Name** | Mahjong Vision Calculator (mahjong-calculator) |
| **Core Objective** | 리치 마작(Riichi Mahjong)의 실시간 득점 계산 및 의사결정을 보조하는 사용자 대면 프론트엔드 애플리케이션을 구축하고, 동시에 마작 인공지능 강화를 위한 대규모 시뮬레이션 및 학습 데이터 텐서 변환 파이프라인을 구축한다. |
| **Vision** | 마작의 복잡한 점수 계산을 단 한 번의 촬영으로 해결하여, 모든 플레이어가 계산의 번거로움 없이 서비스의 본질적인 재미에만 집중할 수 있는 환경을 제공한다. |
| **Target Audience** | - **입문자**: 점수 계산법을 익히지 못해 원활한 진행에 어려움을 겪는 사용자.<br>- **오프라인 플레이어**: 자동 작탁이 없는 환경에서 빠르고 정확한 정산을 원하는 사용자.<br>- **숙련자**: 복잡한 상황에서의 검증 도구로 활용하려는 사용자.<br>- **데이터 과학자**: 강화학습 기반 마작 인공지능 연구를 위한 데이터 생성 필요 사용자. |
| **Architecture Strategy** | 모노레포(Monorepo) 구조 하에서 공통 도메인 로직(Shared Core)을 공유하되, **Application Module (KMP)**과 **Data Generation Module (Python)**을 느슨하게 결합하여 독립적인 빌드 및 배포가 가능하도록 분리한다. |

## 2. Module Definitions

### 2.1. Application Module (app/)
- **Purpose**: Real-time user interaction and stateless scoring.
- **Platform**: iOS (CoreML), Android (TFLite) via Compose Multiplatform.
- **Key Metric**: Latency (< 50ms for calculation) and UI responsiveness.

### 2.2. Learning Data Generation Module (ml-pipeline/)
- **Purpose**: High-throughput simulation and state tensorization for Reinforcement Learning.
- **Platform**: Python (PyTorch, Albumentations).
- **Key Metric**: Throughput (thousands of games/min) and data accuracy.

---


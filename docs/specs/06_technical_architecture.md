# 06. Technical Architecture & Constraints

## 1. Zero-Fragmentation Policy (AGENTS.md)

This project strictly adheres to the Zero-Fragmentation protocol.

- **commonMain**: 99.9% of code (Logic, Models, UI, Navigation).
- **Platform Abstraction**: Only Camera API and ML Inference via Interfaces + DI.

## 2. Module Boundaries (Monorepo)

| Module | Location | Technology | Responsibility |
| :--- | :--- | :--- | :--- |
| **Shared Core** | `app/composeApp/commonMain` | Kotlin | Common Data Models (Tile, Hand, etc.) |
| **App Module** | `app/` | KMP (Voyager, Kotlin-Inject) | UI, Scoring Logic, Camera Integration |
| **ML Module** | `ml-pipeline/` | Python (PyTorch, YOLO) | Data Synthesis, Training, Model Export |

### 2.1. Dependency Rules
- **App/ML -> Shared Core**: Both modules must inherit the same domain logic.
- **App <-> ML**: NO direct dependencies. ML module exports files (`.tflite`, `.mlmodel`) which the App module consumes.

## 3. System Constraints (What NOT to do)

To prevent hallucinations and architectural decay, AI agents must obey these rules:

- **Constraint 1 (No Logic in Platform)**: Do NOT implement business logic in `androidMain` or `iosMain`. Use `expect/actual` only for hardware-specific calls.
- **Constraint 2 (No UI in ML Pipeline)**: The `ml-pipeline` must remain a headless data/training module. No GUI frameworks (Tkinter, Qt) allowed.
- **Constraint 3 (No External Calculators)**: All scoring must happen within the `Shared Core` logic. Do not import 3rd party scoring libraries unless authorized.
- **Constraint 4 (Stateless Engine)**: The scoring engine must NOT depend on persistent state (databases, preferences). It must be a pure function of `(Hand, GameContext) -> Result`.
- **Constraint 5 (Korean Localization)**: All Yaku names and UI strings intended for the user must be in **Korean**.

## 4. Tech Stack

- **UI**: Compose Multiplatform
- **Navigation**: Voyager (Screen/ScreenModel)
- **DI**: Kotlin-Inject (KSP)
- **ML**: YOLOv8/v11 Nano -> TFLite / CoreML
- **AI Pipeline**: PyTorch, Albumentations (Blur, Noise, Perspective)

---


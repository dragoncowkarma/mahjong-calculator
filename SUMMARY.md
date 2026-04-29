<ProjectContext>
<Meta>
  <Purpose>Automated Mahjong score calculator using computer vision (mocked) and rule-based logic for Japanese Riichi Mahjong.</Purpose>
  <TechStack>Kotlin 2.3.20, KMP (Android/iOS), Compose Multiplatform 1.10.3, Voyager 1.1.0-beta02, Coroutines, Flow, Material 3.</TechStack>
  <Architecture>KMP Modular Architecture with Voyager-based MVVM (Screen + ScreenModel). Logic partitioned into Calculator (rules), UI (components), and Models (data).</Architecture>
</Meta>

<DirectoryTree>
composeApp/src/commonMain/kotlin/com/dragoncowkarma/mahcalc/
├── App.kt (Entry MahjongScreen)
├── calculator/
│   ├── MahjongCalculator.kt (Core Orchestrator)
│   ├── YakuCalculator.kt (Han/Yaku Logic)
│   ├── FuCalculator.kt (Fu Calculation)
│   └── ScoreCalculator.kt (Points Calculation)
├── ui/
│   ├── MahjongScreenModel.kt (UI State/Logic)
│   ├── ScoreResultDashboard.kt (UI View)
│   └── TileCorrectionPanel.kt (UI View)
└── models/
    ├── MahjongTile.kt (Data Class)
    └── MatchContext.kt (Game State)
</DirectoryTree>

<CoreModules>
- `MahjongScreen` (App.kt) - Voyager screen entry point; orchestrates main UI layout.
- `MahjongScreenModel` (ui/MahjongScreenModel.kt) - Manages UI state (isCalculating, detectedTiles, resultState) and triggers calculations.
- `MahjongCalculator` (calculator/MahjongCalculator.kt) - Entry point for hand decomposition and yaku/fu detection; inputs hand/context, outputs yaku list/fu.
- `YakuCalculator` (calculator/YakuCalculator.kt) - Evaluates specific Riichi Mahjong yaku (Han) from hand melds and context.
- `ScoreCalculator` (calculator/ScoreCalculator.kt) - Computes final point values based on Han, Fu, and table context.
- `AgariEvaluator` (models/AgariEvaluator.kt) - Validates if a hand configuration is a winning state (Agari).
</CoreModules>

<DataFlow>
1. UI triggers `processCameraFrame` in `MahjongScreenModel`.
2. `ScreenModel` runs detection logic (SpatialTileSorter/NMS) -> updates `detectedTiles` Flow.
3. If 14 tiles detected, `calculateScore` is invoked.
4. `MahjongCalculator.calculate` performs hand decomposition -> calls `YakuCalculator` and `FuCalculator`.
5. `ScoreCalculator` computes final points -> result emitted via `resultState` StateFlow.
6. UI collects flows and updates Material 3 components.
</DataFlow>

<StrictConventions>
- Mandatory KMP Zero-Fragmentation: 99.9% code in `commonMain` or `composeApp`.
- No platform-specific UI: strictly Compose Multiplatform.
- Navigation/State: Must use Voyager `Navigator` and `ScreenModel`.
- Dependency Injection: Use `Kotlin-Inject` (as per AGENTS.md, though current impl uses simple instantiation).
- Resources: All strings/drawables in `commonMain/composeResources`.
- Ban on `expect`/`actual`: Prefer Inversion of Control via Interfaces + Kotlin-Inject.
- Logic constraint: No recursion for hand decomposition (uses iterative state machine).
</StrictConventions>
</ProjectContext>

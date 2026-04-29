# KMP Project Agent Protocol (AGENTS.md)

## 1. Role & Core Invariants (Always)
1. **Primary Goal**: Maintain absolute `Zero-Fragmentation` between platforms.
2. **Execution Rule**: Write 99.9% of code in `commonMain` or `composeApp`.
3. **Never**: Write business logic, UI, or navigation in `androidApp` or `iosApp` modules.
4. **Never**: Use `expect`/`actual` for high-level logic. Use Interfaces + `Kotlin-Inject` instead.

## 2. Tech Stack (Strictly Enforced)
1. **UI**: `Compose Multiplatform`
2. **Navigation**: `Voyager` (`cafe.adriel.voyager:*`)
3. **Asynchronous**: `Coroutines` & `Flow` (`kotlinx.coroutines`)
4. **DI**: `Kotlin-Inject` (`me.tatarka.inject`)
5. **Storage**: `Multiplatform Settings` (`com.russhwolf:multiplatform-settings`)
6. **Serialization**: `kotlinx.serialization`
7. **Resources**: `Compose Multiplatform Resources` (under `commonMain/composeResources`)

## When Starting a Task
1. **Context Check**: Read `SUMMARY.xml` to grasp the latest project architecture and dependencies.
2. **Validation**: Verify that the task can be implemented using the defined tech stack in `commonMain`.
3. **Stop**: If a requirement forces platform-specific UI (e.g., `SwiftUI` or `XML Layouts`). Propose a shared `commonMain` abstraction instead.

## When Writing Code
1. **Location**: All files MUST reside in `commonMain/kotlin/...`.
2. **Navigation**: Implement all screens via the Voyager `Screen` interface.
3. **State**: Manage UI state exclusively via Voyager `ScreenModel`. **Never** use Android `ViewModel` or `LiveData`.
4. **Resources**: Place all strings, drawables, and fonts in `commonMain/composeResources`. Access them via `stringResource()` or `painterResource()`.
5. **Constraint**: If `android.*` or `platform.UIKit.*` imports appear in shared code, **STOP** and remove them. Use DI to bridge platform gaps.

## When Finishing a Task (Definition of Done)
1. **Cross-Platform**: The project compiles for both Android and iOS via `./gradlew build` without platform-specific modifications.
2. **No Leakage**: No new business logic or UI components exist in `androidApp/` or `iosApp/`.
3. **Persistence**: `SUMMARY.xml` is updated with any new files, components, or architectural changes.
4. **Standard**: Code follows the "Always" invariants defined in `Section 1`.

## When Blocked (Escalation Rules)
1. **When Blocked**: If a native API (e.g., Camera, Bluetooth) is required and no common wrapper exists, stop and propose an interface in `commonMain`.
2. **When Outdated**: If `SUMMARY.xml` does not match the current file structure, update `SUMMARY.xml` before proceeding with the task.
3. **When Conflicts**: If a library update breaks the `Zero-Fragmentation` rule, revert and seek a multiplatform alternative.

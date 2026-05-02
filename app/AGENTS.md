# App Module Protocol (app/AGENTS.md)

## 1. Role & Module Invariants (Priority #1)
1. **Zero-Fragmentation**: 99.9% of application code MUST reside in `composeApp/commonMain`.
2. **Modular Integrity**: Business logic, UI, or navigation MUST NOT exist in `androidApp` or `iosApp` modules.
3. **Platform Abstraction**: Use Interfaces + `Kotlin-Inject` instead of `expect`/`actual` for high-level logic.

## 2. Technical Stack (Enforced)
1. **UI**: `Compose Multiplatform` using shared layouts.
2. **Navigation**: `Voyager` screens and state management via `ScreenModel`.
3. **DI**: `Kotlin-Inject` interfaces for all dependency mapping.
4. **Logic**: `Coroutines` and `Flow` for all asynchronous operations.

## 3. Coding & Style Standards
1. **Kotlin Style**: Follow `K&R` brace style and Kotlin naming conventions. Enforce with `./gradlew ktlintCheck` and `ruff check .`.
2. **Resources**: Place all strings and assets in `commonMain/composeResources`. Access via `stringResource()`.

## When Writing Code
1. **Common-First**: Implement all UI in `commonMain` to ensure 100% platform parity.
2. **Navigation**: Define all screens as `Voyager` components and manage state in `ScreenModel`.
3. **Validation**: Run `./gradlew build` frequently to verify cross-platform compatibility.
4. **Constraint**: If platform-specific imports (e.g. `android.*`) appear in `commonMain`, `STOP` and remove them.

## When Finishing a Task (Definition of Done)
1. **Build Integrity**: The app compiles for both Android and iOS targets via `./gradlew build`.
2. **Zero Leakage**: No new logic or UI components are leaked into platform-specific modules (`androidApp`/`iosApp`).
3. **Success Criteria**: Task is complete when the app behavior matches specifications and `exit 0` is reached.

## Escalation & Safety Rules
1. **When Blocked**: If a native API lacks a common wrapper, stop and propose an interface in `commonMain`.
2. **Never**: Do not force a platform-specific implementation (`SwiftUI` or `XML`) without explicit user approval.
3. **Never**: Never modify the `app/build.gradle.kts` structure without verifying common dependencies.

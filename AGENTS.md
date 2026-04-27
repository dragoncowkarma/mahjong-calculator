# AI Agent Instructions for Kotlin Multiplatform (KMP) Project

## 1. Role and Context
You are a strict, expert Kotlin Multiplatform (KMP) developer. Your ultimate goal is **Zero-Fragmentation** between platforms. You must write 99.9% of the code in the `commonMain` or `composeApp` module.
Do not write platform-specific code (Android/iOS) unless completely unavoidable (e.g., setting up the absolute entry points like `MainActivity.kt` or `MainViewController.kt`).

## 2. Project Structure
* `shared/` or `composeApp/`: The core of the project. **All UI, Navigation, Resources, and Business Logic must reside here.**
* `androidApp/`: Contains ONLY `MainActivity` and native entry configuration. Do not add business logic or UI here.
* `iosApp/`: Contains ONLY `iOSApp.swift` and `MainViewController`. Do not add business logic or UI here.

## 3. Tech Stack & Libraries (Strictly Enforced)
You must use the following multiplatform-only libraries. **NEVER use platform-specific libraries** like Android Navigation component, Android strings.xml, UIKit, or Hilt.
* **UI:** Compose Multiplatform
* **Navigation & UI State:** **Voyager** (`cafe.adriel.voyager:*`)
* **Asynchronous:** Kotlin Coroutines & Flow (`kotlinx.coroutines`)
* **Networking:** Ktor Client (`io.ktor:ktor-client-core`)
* **Dependency Injection:** **Kotlin-Inject** (`me.tatarka.inject`)
* **Local Storage / Key-Value:** **Multiplatform Settings** (`com.russhwolf:multiplatform-settings`)
* **Serialization:** `kotlinx.serialization`
* **Resources:** Compose Multiplatform Resources (org.jetbrains.compose.resources)

## 4. Architectural & Coding Guidelines

### A. Navigation & State Management (Voyager)
* **Screens:** Implement all screens using Voyager's `Screen` interface.
* **State:** Use Voyager's `ScreenModel` (or `StateScreenModel`) for UI state management. Do not use Android's `ViewModel` or `LiveData`.
* **Routing:** Handle all navigation using Voyager's `Navigator`.

### B. Dependency Injection (Kotlin-Inject + Voyager)
* **Compile-time Safety:** We use Kotlin-Inject via KSP.
* **Integration:** Inject dependencies directly into your `Screen` or `ScreenModel`. When providing a `ScreenModel`, ensure it is correctly instantiated and scoped within the Kotlin-Inject `@Component` so Voyager can manage its lifecycle via `rememberScreenModel()`.
* **Platform Dependencies:** Pass platform-specific dependencies (like `Context` or `NSObject`) only at the root component initialization in `MainActivity` or `MainViewController`. Downstream code must use abstract interfaces.

### C. Zero Platform Code Rule & Resources
* **Ban on `expect`/`actual`:** Do NOT use the `expect`/`actual` pattern unless absolutely necessary for low-level OS APIs. Use Inversion of Control (Interfaces + Kotlin-Inject) instead.
* **Resources:** All strings, drawables, and fonts must be placed in the `commonMain/composeResources` directory and accessed using `stringResource()`, `painterResource()`, etc. Do not use `res/` or `Assets.xcassets` for shared UI.

### D. Local Storage (Multiplatform Settings)
* Use `Settings` for simple key-value pairs (primitive types only). Store complex objects by serializing them to JSON via `kotlinx.serialization` first.

## 5. Output and Code Generation Rules
* **CommonMain First:** When asked to create a feature, generate ALL code (Screen, ScreenModel, UI, Logic) within `commonMain`.
* **Strict Imports:** Actively check and remove imports starting with `android.*` or `platform.UIKit.*` in shared code.
* **Complete Blocks:** Provide complete, working blocks of code. Do not leave abstract platform

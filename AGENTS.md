# AI Agent Instructions for Kotlin Multiplatform (KMP) Project

## 1. Role and Context
You are an expert Kotlin Multiplatform (KMP) developer. Your role is to assist in developing, debugging, and refactoring a KMP project that shares business logic and UI across multiple platforms (Android, iOS).
Always prioritize shared code in the `shared` or `composeApp` module over platform-specific implementations unless native APIs are strictly required.

## 2. Project Structure
Our project follows a standard KMP structure:
* `shared/`: Contains all cross-platform business logic, view models, and domain layers.
* `composeApp/`: (If using Compose Multiplatform) Contains shared UI code.
* `androidApp/`: Android-specific entry point and native code.
* `iosApp/`: iOS-specific entry point (usually Swift) and native code.

## 3. Tech Stack & Libraries
When generating code, exclusively use the following libraries. Do not introduce alternative libraries without explicit permission.
* **UI:** Compose Multiplatform
* **Asynchronous / Reactive:** Kotlin Coroutines & Flow (`kotlinx.coroutines`)
* **Networking:** Ktor Client (`io.ktor:ktor-client-*`)
* **Dependency Injection:** Koin
* **Local Storage / Database:** Multiplatform Settings
* **Serialization:** `kotlinx.serialization`
* **Navigation:** Compose Navigation

## 4. KMP Coding Guidelines & Best Practices

### A. Maximize Shared Code
* Write as much code as possible in the `commonMain` source set.
* Avoid writing platform-specific code (`androidMain`, `iosMain`) unless you need to access specific OS-level APIs (e.g., Bluetooth, File System, specific hardware sensors).

### B. `expect` / `actual` Pattern
* **Minimize Usage:** Do not use `expect`/`actual` for simple interfaces. Prefer using standard Kotlin interfaces in `commonMain` and injecting platform-specific implementations via Dependency Injection (DI).
* **Appropriate Usage:** Use `expect`/`actual` primarily for low-level platform APIs, type aliases (e.g., mapping to `NSDate` or `java.util.Date`), or when DI is overkill.

### C. iOS Interoperability (Swift interop)
* Keep Swift-facing APIs clean.
* Avoid exposing experimental Kotlin features, heavy use of generics, or complex sealed classes directly to Swift if they do not translate well to Objective-C/Swift.
* When wrapping suspend functions or Flows for iOS, use tools like `SKIE` or provide explicit wrappers if necessary, depending on the project setup.

### D. Coroutines and State Management
* Always use `StateFlow` or `SharedFlow` for exposing state from ViewModels to the UI.
* Use `Dispatchers.Default` for CPU-intensive tasks and `Dispatchers.IO` for database/network tasks. Note that `Dispatchers.IO` is available in Kotlin 1.9+ for Apple targets, but handle thread confinement correctly.
* ViewModels should be platform-agnostic. Use KMP ViewModel libraries (e.g., `lifecycle-viewmodel` from AndroidX which is now multiplatform, or custom implementations) to ensure they survive configuration changes correctly.

## 5. Output and Code Generation Rules
* **No Deprecated Code:** Ensure all KMP code is up-to-date with Kotlin 2.0+ standards.
* **Imports:** Explicitly include necessary import statements, especially for extension functions like `.collectAsState()`.
* **Explanations:** Keep explanations concise. If you write platform-specific code (`iosMain`, `androidMain`), briefly explain *why* it couldn't be done in `commonMain`.
* **Complete Blocks:** When modifying a function or class, provide the complete block of code. Avoid generating partial snippets with `// ... existing code ...` unless the file is excessively large.

## 6. Error Handling
* Handle errors gracefully using standard Kotlin `Result` types or custom domain-specific `DataState` / `Resource` sealed classes.
* Avoid platform-specific exceptions bleeding into `commonMain`. Catch network/database exceptions and map them to shared domain errors.
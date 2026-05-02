# Voyager Migration Strategy

## Overview
This document outlines the strategy for migrating existing Jetpack Compose screens to the **Voyager** routing framework, utilizing **Kotlin-Inject** for dependency injection.

## 1. Migration Status
The core infrastructure for `Voyager` and `Kotlin-Inject` has already been successfully integrated into `libs.versions.toml` and `app/composeApp/build.gradle.kts` without disrupting the existing KMP configurations. 

Existing screens, including `YakuCalculationScreen`, have already been structurally adapted to this model.

## 2. Refactoring Strategy for Existing UI Logic
To safely migrate screens to Voyager without breaking existing logic, the following patterns are enforced:

### Screen Implementation
- Each distinct UI flow must implement Voyager's `Screen` interface.
- Compose components are wrapped inside the overridden `@Composable override fun Content()` block.
- Example: `class YakuCalculationScreen : Screen { ... }`

### State Management via ScreenModel
- Business logic and state must be decoupled from the `Screen` and moved into a `ScreenModel`.
- State is exposed using `StateFlow` and consumed in the UI via `collectAsState()`.
- Example: `class YakuCalculationScreenModel @Inject constructor() : ScreenModel { ... }`

### Dependency Injection Integration
- **Kotlin-Inject** handles `ScreenModel` instantiation.
- Screens access their respective models via `rememberScreenModel` leveraging the app's root component.
- Example:
  ```kotlin
  val component = LocalAppComponent.current
  val screenModel = rememberScreenModel { component.yakuCalculationScreenModel }
  ```

### Navigation Execution
- Navigation relies on Voyager's `LocalNavigator.currentOrThrow`.
- Operations like `navigator.push()` and `navigator.pop()` govern screen transitions, ensuring the back-stack remains strictly decoupled from native platform implementations.

## 3. Conclusion
The initial migration is complete. Moving forward, all new screens added to the `app/` module must adhere to this `Screen` and `ScreenModel` contract as dictated by `AGENTS.md`.

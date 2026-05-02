# 040 - Expansion: Execution Command

### 1. [Jules] [Parallel-Ready: Scope A] Shanten Calculation Engine
- **Scope**: `app/composeApp/src/commonMain/kotlin/.../logic/shanten/`
- **Action**:
    - **Pre-flight**: Read root `AGENTS.md` and `app/AGENTS.md` for algorithmic complexity rules.
    - Implement the combinatorial algorithm to determine distance to Tenpai (Shanten).
    - Provide "best discard" suggestions logic based on Shanten reduction efficiency.
    - Validate with unit tests for Kokushi, Chiitoitsu, and Standard hand shapes.

---

### 2. [Antigravity: Claude Opus 4.6] Persistence & History Database
- **Task**: Implement local storage using SQLDelight.
- **Action**:
    - **Pre-flight**: Read `app/AGENTS.md` for persistence and database patterns.
    - Define the SQLDelight schema for Hand history, scoring results, and timestamps.
    - Implement the `HistoryRepository` and the corresponding `HistoryScreen` UI.
    - Ensure all database operations are handled asynchronously via Coroutines.

---

### 3. [Jules] [Parallel-Ready: Scope B] Multi-Language Localization
- **Scope**: `app/composeApp/src/commonMain/resources/`
- **Action**:
    - **Pre-flight**: Review root `AGENTS.md` for bilingual documentation and string standards.
    - Build string resource XMLs for Korean (Default), Japanese, and English.
    - Implement automated localization mapping across all UI components using `stringResource()`.

---

### 4. [Antigravity: Gemini 3.1 Pro] Beta Deployment & Telemetry
- **Task**: Prepare for release and monitor performance.
- **Action**:
    - **Pre-flight**: Review root `AGENTS.md` for release management protocols.
    - Integrate `Firebase` Crashlytics and Analytics for multiplatform telemetry.
    - Implement an anonymized feedback loop for collecting misidentified image data.
    - Optimize the final build size for App Store and Play Store distribution.

---

### 5. [Gemini CLI] CI/CD & Artifact Generation
- **Task**: Final release packaging.
- **Action**:
    - **Pre-flight**: Sync `SUMMARY.xml` and verify `AGENTS.md` compliance for all final files.
    - Configure `Fastlane` lanes for automated Beta/Internal distribution.
    - Generate final release artifacts (`./gradlew bundleRelease` or IPA export).
    - Verify all documentation in `SUMMARY.xml` is fully synchronized.

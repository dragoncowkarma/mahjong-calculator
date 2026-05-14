# Task 01501: Calculator Edge Cases Implementation

## Objective
Enhance the `MahjongCalculator` unit test coverage and logic to handle high-complexity Riichi Mahjong edge cases, ensuring robust validation before proceeding to ML integration.

## Scope
- Files: `app/shared/src/commonTest/.../MahjongCalculatorTest.kt` (or equivalent test directory), `app/shared/src/commonMain/.../MahjongCalculator.kt`.
- Constraint: Do NOT modify any UI components or ML pipeline scripts.

## Tasks
1. **Complex Wait Patterns (다면팅):** Add comprehensive unit tests for complex wait patterns such as Ryanmen, Kanchan, Penchan, Tanki, and Shanpon combinations. Ensure the calculator correctly identifies the winning tile and the optimal hand composition.
2. **Furiten (후리텐):** Implement and test the Furiten validation logic. Ensure the calculator correctly flags hands that are in a Furiten state (both general and temporary) and prohibits Ron declarations.
3. **Pinfu Fu (핀후 부수):** Add specific tests to verify that Pinfu hands strictly return 20 Fu for Tsumo and 30 Fu for Ron, correctly handling the edge cases where a wait pattern might conflict with the Pinfu requirements.
4. **Local Rules (로컬 룰):** Add configuration options or specific tests for handling local rules, such as Akadora (Red Five) specific scoring and Kuikae (swap calling) prohibitions.

## Definition of Done (DoD)
1. All new and existing `MahjongCalculator` tests pass successfully (`./gradlew test`).
2. Code coverage for `MahjongCalculator` reaches > 95% for edge case scenarios.
3. Code changes adhere strictly to the rules defined in `AGENTS.md` and `SUMMARY.xml`.
4. The project compiles without warnings or errors.

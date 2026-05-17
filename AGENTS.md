# 🤖 Project Agent Protocol (AGENTS.md)

[CRITICAL] This document defines the base standards for the `Mahjong Calculator` project. Specific rules are located in:
1. `app/AGENTS.md`: KMP Client standards.
2. `ml-pipeline/AGENTS.md`: Python ML Pipeline standards.
3. `docs/AGENTS.md`: Documentation and Planning standards.

## When Starting a Task
1. **Context Awareness**: Read `SUMMARY.xml` prior to accessing any files to prevent redundant operations.
2. **Refactor Tracking**: Check [`REFACTOR_TRACKING.md`](REFACTOR_TRACKING.md) immediately. Resolve entries within scope and delete them from the file once done.
3. **Ambiguity**: If requirements are unclear or contradictory, **Stop** and call `ask_question` or wait for user feedback in planning mode.
4. **Planning**: For complex tasks, architectural changes, or significant deviations, **Stop** and propose an `implementation_plan.md`.

## When Modifying Code
1. **Scope Restriction**: Modifications MUST be strictly limited to files defined in `SUMMARY.xml`.
2. **No Boilerplate**: Prohibit unrequested boilerplate generation; focus on `Task` scope only.
3. **Closure**: Every task MUST result in an `exit 0` state or a valid build.
4. **Mimicry**: Adapt to the established local coding style found in `app/` and `ml-pipeline/`.

## When Blocked
1. **Technical Debt**: If a task requires bypassing a protocol, log it in [`REFACTOR_TRACKING.md`](REFACTOR_TRACKING.md) and report to the `USER`.
2. **Linter Failures**: If the project linter (`agent_md_linter.py`) fails on any modified `AGENTS.md`, fix the anti-patterns immediately.
3. **Never**: Do not delete core architecture files or bypass the `SUMMARY.xml` update requirement.
4. **Never**: Never modify files outside the scope defined in `SUMMARY.xml` without explicit authorization.

## Agent Restrictions
1. **Parallel Execution**: Parallel task processing is EXCLUSIVELY permitted for the `Jules` agent.
2. **Gemini CLI Constraints**: Due to environment/`MCP` limitations, `Gemini CLI` must NOT be used for `UI` modification or resource-heavy tasks.

### 🛑 COMMAND EXECUTION SAFETY RULES (터미널 실행 안전 규칙)

1. **비대화형(Non-interactive) 모드 강제:**
   - 파이썬 스크립트나 `pip` 설치 등을 실행할 때는 절대 사용자의 입력(Y/N)을 기다리지 않도록 환경 변수를 설정하세요.
   - 예시: `YOLO_VERBOSE=False`, `CI=true` 환경 변수를 주입하여 텔레메트리나 상호작용 프롬프트를 무시하세요.

2. **무한 Polling 방지 및 Timeout 강제:**
   - 명령어를 실행한 후 `Checked command status`를 **연속 5회 이상 반복하지 마세요.**
   - 5회 확인 후에도 프로세스가 끝나지 않았다면, 프로세스가 멈춘(Hanging) 것으로 간주하고 즉시 해당 프로세스를 Kill(종료) 하세요.
   - 긴 시간이 필요한 작업이라면 리눅스의 `timeout` 명령어를 활용하세요. (예: `timeout 120s ./.venv/bin/python export_model.py`)

3. **로그 파일 리다이렉션 (Log Redirection):**
   - 출력이 많은 ML 모델 익스포트/트레이닝 명령어를 실행할 때는 표준 출력과 에러를 파일로 리다이렉션하세요.
   - 예시: `./.venv/bin/python src/export/export_model.py > export_log.txt 2>&1 &`
   - 프로세스가 예상보다 길어지면 상태 체크를 멈추고 `cat export_log.txt`를 통해 현재 로그의 마지막 부분을 읽어 문제 원인을 파악하세요.

## Definition of Done (DoD)
A task is complete when ALL of the following pass:
1. `SUMMARY.xml` is updated with all new files and component paths.
2. The project-wide build command `./gradlew build` (for `app/`) or `ruff check .` (for `ml-pipeline/`) exits 0.
3. All modified files end with exactly one blank line (verified by `tail -c 1`).
4. `python3 ~/Desktop/agent-md-linter/agent_md_linter.py AGENTS.md` exits 0 if any protocol file was modified.
5. The code satisfies 100% of the `USER_REQUEST`.

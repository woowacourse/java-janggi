# Cycle 2 - PR Review v1 - 수정 사항

---

## ✅ 리팩토링 할 것 목록

- [x] **1. `Board`의 `calculateScore`를 `Team`에서 처리하도록 수정.**
- [x] **2. `isColumnClearBetween`의 축약형 이름 없애기**
- [x] **3. `JanggiGame`에서 DB 전용 getter 제거 (`getStateName`, `getArrangementOf`)**
  - `JanggiGame`에 `getGameState()` 추가
  - `GameConsole.saveGame()`에서 `game.getGameState().stateName()`, `game.getGameState().getArrangementOf(team)` 로 직접 호출
  - `GameState.stateName()`, `GameState.getArrangementOf(Team)` 는 그대로 유지
- [x] **4. `GameStateName` enum 도입 및 `GameState.stateName()` 타입 변경**
  - `GameStateName { READY_HAN, READY_CHO, PLAYING, BIKJANG, END }` enum 생성
  - `GameState.stateName()`의 default 제거 → abstract 메서드로 변경, 반환 타입 `String` → `GameStateName`
  - 각 구현체(ReadyState, PlayingState, BikjangState, EndGameState)에서 직접 반환
  - `H2GameRepository.updateState()` 파라미터 `String` → `GameStateName` 으로 변경 (DB 저장 시 `.name()` 사용)
  - `GameStateName`에 팩토리 메서드 `toGameState(GameDto)` 추가 → 문자열 → GameState 변환 책임을 도메인으로 이동
  - `GameConsole.restoreState()`를 `GameStateName.toGameState(snapshot)` 한 줄 호출로 교체
- [x] **5. `PlayingState`의 detector들을 static으로 수정**
- [x] **6. `GameConsole` 안쓰는 import 제거**
- [x] **7. 재귀를 통한 재입력 시도를 while로 수정하기.**

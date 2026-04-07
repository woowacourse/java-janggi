# Cycle 2 - PR Review v1 - 수정 사항

---

## ✅ 리팩토링 할 것 목록

- [x] **1. null 정리하기**
  - 없을 수 있는 값은 Optional, 없을 수 없는 값은 예외 던지기로 처리
- [x] **2. `PlayingState`의 `resolveAfterMove` 함수 분리**
  - 상대 패배 조건
  - 보드 전체 상태로 인한 특수 종료 케이스
  - 합법적 수 존재 여부
- [x] **3. DB의 createdAt을 없애고, id 기반으로 데이터 sort하도록 수정**
- [x] **4. restoreGame()에서 state를 먼저 확인하고 분기하기**
  - restoreGame()에서 stateName으로 분기 - snapshot.stateName()이 SETUP인지 확인
  - JanggiGame에 Board 없는 생성자 추가 - SetupState 복원용

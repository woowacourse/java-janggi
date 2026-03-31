# Cycle 1 - PR Review v1 - 수정 사항

---

## ✅ 리팩토링 할 것 목록

- [ ] **1. `Col`의 `checkExistingColumn()` 발생하지 않는 `Exception` 없애기**
- [ ] **2. `PositionLayout`의 두 메서드가 같은 키를 쓰지 않는다는 전제를 강제하도록 하기. (호출 순서의 의존하지 말 것)**
  - [ ] 1안 : `PositionLayout`의 `placeHanPieces`와 `placeHanInnerPieces`를 B/C/G/H를 포항해서 전체 배치를 한번에 구성하도록 수정
  - [ ] 2안 : `result.put()` 대신 이미 키가 존재하면 예외를 던지도록 수정
- [ ] **3. `PositionLayout`의 `placeCHoInnerPieces` 네이밍 수정**
- [ ] **4. `Arrangements`의 `assignArrangement`의 `Map` 원본 복사 후 put 하도록 수정**
- [ ] **5. 사용되지 않는 import 삭제**

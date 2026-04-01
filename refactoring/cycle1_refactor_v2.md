# Cycle 1 - PR Review v2 - 수정 사항

---

## ✅ 리팩토링 할 것 목록

- [x] **1. GeneralMovement와 GuardMovement를 FourDirectionMovement 하나로 통합하고, StepPieceMovement도 LinearMovement와 같이 이동 방식을 드러내는 이름으로 수정**
- [ ] **2. MovementValidator를 MovementValidator를 기물 타입별로 분리(예: ChariotValidator, CannonValidator)하고 팩토리로 관리하는 방향으로 수정**
- [ ] **3. StepPieceMovement는 차, 포 이고, Piece.isStepPiece는 마, 상으로 처리하는거 수정해주기**
- [ ] **4. GameState의 nextTurn을 JanggiGame에서 처리하도록 수정하기**

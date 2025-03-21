## 체크 리스트

- [x] 미션의 필수 요구사항을 모두 구현했나요?
- [x] Gradle `test`를 실행했을 때, 모든 테스트가 정상적으로 통과했나요?
- [x] 애플리케이션이 정상적으로 실행되나요?
- [x] [프롤로그](https://prolog.techcourse.co.kr/studylogs/4077)에 셀프 체크를 작성했나요?

## 객체지향 생활체조 요구사항을 얼마나 잘 충족했다고 생각하시나요?

### 1~5점 중에서 선택해주세요.

- [ ] 1 (전혀 충족하지 못함)
- [x] 2
- [ ] 3 (보통)
- [ ] 4
- [ ] 5 (완벽하게 충족)

### 선택한 점수의 이유를 적어주세요.

- 게임 흐름을 READY, MOVE, END를 갖는 상태 패턴으로 적용하고 싶었는데 적용하지 못했습니다.
- `JanggiBoard`에 if문이 많아 가독성이 떨어지는 것 같습니다.

## 어떤 부분에 집중하여 리뷰해야 할까요?

### 한글 네이밍

예를 들어 병을 영어로 solider로 했을 때, 직관성이나 이해가 부족할 것 같아서였습니다.
한글 네이밍이 더 직관적이고 명확하다고 생각했는데,
이 점에 대해 어떻게 생각하시는지 궁금합니다.

### JanggiBoard의 책임

저는 장기 보드의 역할이 `기물 진행경로 검증하기 & 움직이기` 라고 생각합니다.
그러다보니 메소드가 많아지고 복잡해졌는데 책임 분리가 잘못된건지 궁금합니다.

### 포의 규칙

`포는 포를 뛰어넘을 수 없다`, `포는 포를 잡을 수 없다`와 같은 규칙들은 `포`만이 갖고 있는 복잡한 규칙인데,
이 규칙들에 대한 분기처리를 현재 `JanggiBoard`에서 해주고 있습니다.

#### JanggiBoard.java

움직이는 피스가 `포일때`, `포가 아닐 때` 다른 메서드를 호출합니다.

```java
    public void move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
    afterPosition.validateBound();
    Piece piece = getPieceFrom(beforePosition);

    if (piece.getClass().equals(포.class)) {
        move포(piece, beforePosition, afterPosition);
    }
    if (!piece.getClass().equals(포.class)) {
        moveOtherPiece(piece, beforePosition, afterPosition);
    }
    validateDestinationPiece(piece, afterPosition);
    janggiBoard.put(afterPosition, piece);
}
```

페어랑 많이 고민해보았지만 `move` 메서드를 다른 객체로 분리할 수 있는 방법이 없다고 판단했습니다.
이를 더 효율적으로 작성할만한 방법이 있을지 궁금합니다!

### Piece가 가지는 책임

#### `Piece`에 `Position`을 도입하지 않은 이유

- 기물이 위치를 가지고 있을 필요가 없다고 생각하여 Piece 클래스의 필드로 `Position`을 넣어주지 않았습니다.
- `Position` 값은 계속해서 바뀌게되므로 외부에서 움직일 때마다 주입해주어야 합니다. 그럼 setter의 사용이 불가피해집니다.

#### `Piece`에 `Position`을 도입하고 싶은 이유

- 기물 자체가 스스로 움직이므로 위에서의 고민이었던 `JanggiBoard`에서 `move` 메서드를 단순화할 수 있을 것 같습니다.
- 제로는 기물이 위치를 갖고 있는 것에 대해서 어떻게 생각하는지 궁금합니다!

### 상태 패턴 도입에 관한 질문

이전 미션까진 진행 상태를 객체의 필드값으로 저장하도록 구현했습니다.
`Piece`에 상태 패턴을 도입하고 싶은데, 이에 대해 어떻게 생각하시는지 궁금합니다!
만약 하게된다면, 모든 기물에 대해 상태를 저장하는 객체를 생성 후,
움직일 수 있는지 검증하고 상태를 변경해 줄 것 같습니다.

혹시 제로는 상태 패턴을 자주 사용하시나요? 상태 패턴을 설계할 때 팁이 있으면 듣고 싶습니다.
그리고 `Piece`에 상태 패턴을 도입한다면 어떤 상태들로 나눌 수 있을지가 궁금합니다!

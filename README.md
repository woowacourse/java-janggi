# java-janggi

장기 미션 저장소

## 출력 포맷

```
  0 │ C  M  E  S  ·  S  E  M  C
  1 │ ·  ·  ·  ·  G  ·  ·  ·  ·
  2 │ ·  P  ·  ·  ·  ·  ·  P  ·
  3 │ J  ·  J  ·  J  ·  J  ·  J
  4 │ ·  ·  ·  ·  ·  ·  ·  ·  ·
  5 │ ·  ·  ·  ·  ·  ·  ·  ·  ·
  6 │ j  ·  j  ·  j  ·  j  ·  j
  7 │ ·  p  ·  ·  ·  ·  ·  p  ·
  8 │ ·  ·  ·  ·  g  ·  ·  ·  ·
  9 │ c  m  e  s  ·  s  e  m  c  
    ────────────────────────────
      a  b  c  d  e  f  g  h  i

  초나라 차례입니다.
  
  이동할 기물을 선택해 주세요.
  c6
  
  해당 기물은, b6, c5, d 으로 이동할 수 있습니다.
  이동할 위치를 선택해 주세요.
  c5
  
  0 │ C  M  E  S  ·  S  E  M  C
  1 │ ·  ·  ·  ·  G  ·  ·  ·  ·
  2 │ ·  P  ·  ·  ·  ·  ·  P  ·
  3 │ J  ·  J  ·  J  ·  J  ·  J
  4 │ ·  ·  ·  ·  ·  ·  ·  ·  ·
  5 │ ·  ·  j  ·  ·  ·  ·  ·  ·
  6 │ j  ·  ·  ·  j  ·  j  ·  j
  7 │ ·  p  ·  ·  ·  ·  ·  p  ·
  8 │ ·  ·  ·  ·  g  ·  ·  ·  ·
  9 │ c  m  e  s  ·  s  e  m  c  
    ────────────────────────────
      a  b  c  d  e  f  g  h  i
      
  한나라 차례입니다.
  
  이동할 기물을 선택해 주세요.
  i3
  
  해당 기물은, h3, i4 으로 이동할 수 있습니다.
  이동할 위치를 선택해 주세요.
  h3
  
  0 │ C  M  E  S  ·  S  E  M  C
  1 │ ·  ·  ·  ·  G  ·  ·  ·  ·
  2 │ ·  P  ·  ·  ·  ·  ·  P  ·
  3 │ J  ·  J  ·  J  ·  J  J  ·
  4 │ ·  ·  ·  ·  ·  ·  ·  ·  ·
  5 │ ·  ·  j  ·  ·  ·  ·  ·  ·
  6 │ j  ·  ·  ·  j  ·  j  ·  j
  7 │ ·  p  ·  ·  ·  ·  ·  p  ·
  8 │ ·  ·  ·  ·  g  ·  ·  ·  ·
  9 │ c  m  e  s  ·  s  e  m  c  
    ────────────────────────────
      a  b  c  d  e  f  g  h  i
```

## 객체 설계

### 장기 판
- [x] 장기판(JanggiBoard)
  - 역할: 좌표와 기물 연결
  - 좌표 Position
  - [x] 행동: 다른 기물을 고려하여 이동 후보군 필터링

- [x] 보드 초기화(BoardInitializer)
  - 역할: 초기 장기 보드에 기물 배치
  - [x] 행동: 초기화

- [x] 경로(Route) 
  - 역할: 기물의 이동 경로 저장
  - 이동 경로
  - [x] 행동: 경로 추가
  - [x] 행동: 중간 경로 반환
  - [x] 행동: 최종 목적지 반환

- [x] 좌표(Position)
  - x, y
  - [x] 행동: 좌표로 이동
  - [x] 행동: 범위 내 좌표 검증

- [x] 이동 방향(Direction)
  - deltaX, deltaY
  - [x] 행동: deltaX, deltaY 반환

### 장기 기물
- Piece (interface)
  - 역할: 모든 기물의 기반

  - [x] 빈 칸 (Empty)
    - [x] 행동: 이동 가능한 모든 경우 계산 시도 시 예외
    - [x] 행동: 이동 가능한 경우 중 불가능한 경우 필터링 시도 시 예외
    - [x] 행동: 기물 타입 반환
    - [x] 행동: side 물을 시 false 반환
      - Board 출력 시 Map을 순회하며 side를 검사한 후 색을 넣어주기 때문에, 예외 처리 불가  


  **인터페이스의 구현 클래스** 

- LimitMovable (추상 클래스)
  - 제한된 횟수의 움직임을 가진 기물
  - 상태: 진영
  - [x] 행동: 이동 가능한 경우 중 불가능한 경우 필터링
  - [x] 행동: 진영 반환

  **상속 받는 객체**
  - [x] 졸 / 병(Soldier)
    - [x] 행동: 이동 가능한 모든 경우 계산
    - [x] 행동: 기물 타입 반환
  - [x] 상(Elephant)
    - [x] 행동: 이동 가능한 모든 경우 계산
    - [x] 행동: 기물 타입 반환
  - [x] 마(Horse)
    - [x] 행동: 이동 가능한 모든 경우 계산
    - [x] 행동: 기물 타입 반환
  - [x] 사(Guard)
    - [x] 행동: 이동 가능한 모든 경우 계산
    - [x] 행동: 기물 타입 반환
  - [x] 궁(King)
    - [x] 행동: 이동 가능한 모든 경우 계산
    - [x] 행동: 기물 타입 반환
  

  - UnLimitMovable (추상 클래스)
    - 무한한 횟수의 움직임을 가진 기물 
    - 상태: 진영
    - [x] 행동: 이동 가능한 모든 경우 계산 (사방으로 직선 방향 동일)
    - [x] 행동: 이동 가능한 경우 중 불가능한 경우 필터링
    
    **상속 받는 객체**
    - [x] 포(Cannon)
      - [x] 행동: 기물 타입 반환
    - [x] 차(Chariot)
      - [x] 행동: 기물 타입 반환

# Step2
## 구현 사항 정리
### 스코어 넣기
  - 궁: -
  - 차: 13
  - 포: 7
  - 마: 5
  - 상: 3
  - 사: 3
  - 졸(병): 2

### 궁성 이동
- **궁과 사는 궁성 밖으로 나갈 수 없다.**
  - 궁과 사는 사실상 보드의 크기가 궁성의 영역만큼이다.
- **차와 포는 대각선 이동 가능 위치일 때만 다른 이동**
  - 궁성 4개의 꼭지점이 아니면 궁성에서의 다른 움직임이 없다.
- **마와 상의 첫 이동은 궁성의 대각선 이동이 될 수 없다. 무조건 첫 이동은 직선 이동이어야 한다.**
  - 마와 상은 궁성에서의 움직임을 구현하지 않아도 된다.
- **졸은 궁성에서도 전진만 가능하다** 
  - 궁성에서 전진 방향으로 5가지 움직임

---
## 객체 설계 
### (추가된 부분만 작성하였습니다.)


**일단 필터링에서 궁성일때 아닐때를 검사해서 해보자**
**Position이 보드 범위를 아니까 move에서 필터링해도 되지 않을까?**
- Position이 궁성 영역을 알아도 될까?
  - 알아도 된다면, 궁성 영역일 때 또한, move를 필터링해도 되지 않을까?
  - 그러면 기물이 뭔지 알아야 하지 않을까?

Position에서 궁성의 위치를 알도록 했습니다. 그리고, 이전에는 Piece에서 다른 기물과 범위를 고려하지 않고, 가능한 모든 경우를 다 계산하고 이후에 필터링을 했습니다. 
하지만, 어차피 Position이 궁성과, 보드의 범위를 알고, Position이 move()를 해주기 때문에, 가능한 경우를 계산할 때, 범위를 벗어나면 추가되지 않도록 했습니다.

**PieceType으로 데이터베이스에 저장하면, 보드를 다시 만들 때 어떻게 기물 구현체로 바꾸지?**

DAO에서 객체까지 만들어줘도 될까? 서비스에서 만들어야 할까?

Application에서 JanggiGame의 적절한 메서드를 불러오도록

JanggiGame이 사실상 Controller 역할을 해서 PieceService도 JanggiGame에서 받아 로직을 수행하는데, PieceController 같은 것을  
만들어서 해야할지 고민입니다.

---

### 테이블 생성 시에 사용한 쿼리 

CREATE TABLE Piece (
piece_id INT AUTO_INCREMENT NOT NULL,
type VARCHAR(20) NOT NULL,
side VARCHAR(10) NOT NULL,
PRIMARY KEY (piece_id)
);

CREATE TABLE BoardPiece (
board_piece_id INT AUTO_INCREMENT NOT NULL,
piece_id INT NOT NULL,
game_id INT NOT NULL,
x INT NOT NULL,
y INT NOT NULL,
PRIMARY KEY (board_piece_id),
UNIQUE(x, y),
FOREIGN KEY (piece_id) REFERENCES Piece(piece_id) ON DELETE CASCADE,
FOREIGN KEY (game_id) REFERENCES Game(game_id) ON DELETE CASCADE
);

CREATE TABLE Game (
game_id INT AUTO_INCREMENT NOT NULL,
state VARCHAR(10) NOT NULL,
PRIMARY KEY (game_id)
);
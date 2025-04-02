# java-janggi

장기 미션 저장소

### 실행 전 해야할 것
1. docker 폴더의 하위에 docker-compose.yml 가 있습니다.
2. 터미널에서 docker 폴더로 이동해 'docker-compose -p janggi up -d' 로 데이터베이스를 띄운다.
3. mysql 서버가 실행되면 애플리케이션을 실행한다.

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

### 데이터베이스 연결
- 테이블
  - Piece
    - piece_id, type, side 컬럼을 가짐
  - BoardPiece 
    - board_piece_id, piece_id, game_id, x, y 컬럼을 가짐
    - 보드 상에 올라가는 기물을 구현
  - Game
    - game_id, state 컬럼을 가짐
    - state에는 진행 중인 턴 또는 게임 종료를 가짐

- Dao를 통해 데이터베이스와 직접 소통
- Service를 통해 사용할 수 있는 타입으로 가공

- Empty 기물은 데이터베이스에 저장하지 않는다.
  - BoardPiece 테이블에서 데이터를 가져오고, 테이블에 없는 좌표는 Empty로 채운다.
- 매 턴마다 Game 테이블의 state에 turn을 저장해준다.
- 매 턴마다 기물이 움직이면 기물의 위치를 데이터베이스에 갱신해준다.

- 게임방은 존재하지 않는 상태로, 게임 테이블의 가장 큰 game_id를 가져와 보드를 가져온다.

---
## 궁성 구현 시 변경점

- Position
  - 궁성 내부의 위치인지 리턴
  - 대각선 이동 가능한 위치인지 리턴

- Guard, King
  - 직선 이동, 대각선 이동으로 분리
    - 궁과 사는 궁성에서만 이동 가능하므로
    
- Solider, UnLimitMovable
  - 대각선 이동 가능한 위치인 경우 대각선 이동 경우 계산

---

### 테이블 생성 시에 사용한 쿼리 
```
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

```
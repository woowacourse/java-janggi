# java-janggi

장기 미션 저장소

### 구현 기능 목록
- 보드 초기화 
  - [x] 장기판 가로 10칸 X 세로 9칸
  - [x] 기물 총 개수: 각 16개 * 2팀

- 팀
  - [X] 한(RED), 초(GREEN)
  - [X] 한의 기물은 대문자로 표시한다.
  - [X] 초의 기물은 소문자로 표시한다.
  - [X] 처음 게임을 시작할 때 초부터 수를 놓는다.

- 기물 이동
  - [x] general: 각 1개, 상하좌우 1칸, 막히지 않았을 시 이동 가능
  - [x] guards: 각 2개, 상하좌우 1칸, 막히지 않았을 시 이동 가능
  - [x] horses: 각 2개, 직선 1칸+대각선 1칸, 막히지 않았을 시 이동 가능
  - [x] elephants: 각 2개, 직선 1칸+대각선 2칸, 막히지 않았을 시 이동 가능
  - [x] chariots: 각 2개, 상하좌우 중 한방향으로 n칸, 막히지 않았을 시 이동 가능
  - [x] cannons: 각 2개, 상하좌우 중 한방향으로 n칸, 반드시 기물 하나만 넘어야 이동 가능, 동일한 기물은 불가
  - [x] soldiers: 각 5개, 상좌우 1칸, 막히지 않았을 시 이동 가능

### 추가 구현 기능 목록
- [X] 궁성
  - [X] general : 궁성 내부에서만 1칸 이동 가능
  - [X] guards : 궁성 내부에서는 대각선으로도 1칸 이동 가능
  - [X] chariots : 궁성 내부에서는 대각선 중에서도 한방향으로 n칸 이동 가능 
  - [X] soldiers : 궁성 내부에서는 전진 방향 대각선 중에서도 1칸 이동 가능

- [X] 점수 출력
  - [X] 각 기물별로 점수가 부여되어 있다.
  - [X] 기물이 잡힐 시 해당 기물에 부여된 점수가 차감된다.
    - [X] 초의 기본 점수는 72점이다.
    - [X] 한의 기본 점수는 73.5점이다.
  - [X] 상대방의 왕을 잡을 경우, 해당 플레이어의 승리로 게임은 종료되고 승패 결과와 양 플레이어의 점수를 노출한다.
  - [X] 게임을 중간에 종료할 경우, 양 플레이어의 점수를 비교해 승패를 결정하고 승패 결과와 양 플레이어의 점수를 노출한다.

- [X] 이전 게임 다시 실행
  - [X] 게임을 중간에 종료한 경우, 이전 게임을 이어서 진행한다.

- [X] DB 연동

### 실행 예시 결과
```

CHER.REHC 1
....G.... 2
.N.....N. 3
S.S.S.S.S 4
......... 5
......... 6
s.s.s.s.s 7
.n.....n. 8
....g.... 9
cehr.rehc 10
123456789

게임을 일시 종료하시겠습니까?(y, n)
n

움직일 말을 알려주세요.
1 10
도착지를 알려주세요.
1 9

CHER.REHC 1
....G.... 2
.N.....N. 3
S.S.S.S.S 4
......... 5
......... 6
s.s.s.s.s 7
.n.....n. 8
c...g.... 9
.ehr.rehc 10
123456789

게임을 일시 종료하시겠습니까?(y, n)
n

움직일 말을 알려주세요.
1 1

도착지를 알려주세요.
1 3

게임을 일시 종료하시겠습니까?(y, n)
y

.HER.REHC 1
....G.... 2
CN.....N. 3
S.S.S.S.S 4
......... 5
......... 6
s.s.s.s.s 7
.n.....n. 8
c...g.... 9
.ehr.rehc 10
123456789
```
### DDL
```
CREATE TABLE `board` (
  `board_id` int NOT NULL,
  `current_team` enum('red','green') NOT NULL,
  PRIMARY KEY (`board_id`),
  KEY `current_team_id` (`current_team`)
);

CREATE TABLE `piece` (
  `x` int NOT NULL,
  `y` int NOT NULL,
  `team` enum('red','green') NOT NULL,
  `piece_type` enum('cannon','chariot','elephant','general','soldier','guard','horse') NOT NULL,
  `is_catch` tinyint(1) NOT NULL,
  `board_id` int NOT NULL,
  `piece_id` int NOT NULL,
  PRIMARY KEY (`piece_id`),
  KEY `fk_piece_my_team` (`team`),
  KEY `fk_piece_piece_type` (`piece_type`),
  KEY `fk_piece_board` (`board_id`),
  CONSTRAINT `fk_piece_board` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
);

INSERT INTO `piece` (`piece_id`, `x`, `y`, `team`, `piece_type`, `is_catch`, `board_id`) VALUES
(1, 1, 10, 'green', 'chariot', 0, 1),
(2, 9, 10, 'green', 'chariot', 0, 1),
(3, 2, 10, 'green', 'elephant', 0, 1),
(4, 7, 10, 'green', 'elephant', 0, 1),
(5, 3, 10, 'green', 'horse', 0, 1),
(6, 8, 10, 'green', 'horse', 0, 1),
(7, 4, 10, 'green', 'guard', 0, 1),
(8, 6, 10, 'green', 'guard', 0, 1),
(9, 5, 9, 'green', 'general', 0, 1),
(10, 2, 8, 'green', 'cannon', 0, 1),
(11, 8, 8, 'green', 'cannon', 0, 1),
(12, 1, 7, 'green', 'soldier', 0, 1),
(13, 3, 7, 'green', 'soldier', 0, 1),
(14, 5, 7, 'green', 'soldier', 0, 1),
(15, 7, 7, 'green', 'soldier', 0, 1),
(16, 9, 7, 'green', 'soldier', 0, 1),
(17, 1, 1, 'red', 'chariot', 0, 1),
(18, 9, 1, 'red', 'chariot', 0, 1),
(19, 3, 1, 'red', 'elephant', 0, 1),
(20, 7, 1, 'red', 'elephant', 0, 1),
(21, 2, 1, 'red', 'horse', 0, 1),
(22, 8, 1, 'red', 'horse', 0, 1),
(23, 4, 1, 'red', 'guard', 0, 1),
(24, 6, 1, 'red', 'guard', 0, 1),
(25, 5, 2, 'red', 'general', 0, 1),
(26, 2, 3, 'red', 'cannon', 0, 1),
(27, 8, 3, 'red', 'cannon', 0, 1),
(28, 1, 4, 'red', 'soldier', 0, 1),
(29, 3, 4, 'red', 'soldier', 0, 1),
(30, 5, 4, 'red', 'soldier', 0, 1),
(31, 7, 4, 'red', 'soldier', 0, 1),
(32, 9, 4, 'red', 'soldier', 0, 1);
```

# java-janggi

장기 미션 저장소

### 기능 구현 목록
- 기물 초기화 
  - [x] 장기판 가로 10개 X 세로 9개
  - [x] 기물 총 개수: 각 16개
- 기물 이동
  - [x] general: 각 1개, 상하좌우 1칸, 막히지 않았을 시 이동 가능
  - [x] guards: 각 2개, 상하좌우 1칸, 막히지 않았을 시 이동 가능
  - [x] horses: 각 2개, 직선 1칸+대각선 1칸, 막히지 않았을 시 이동 가능
  - [x] elephants: 각 2개, 직선 1칸+대각선 2칸, 막히지 않았을 시 이동 가능
  - [x] chariots: 각 2개, 상하좌우 n칸, 막히지 않았을 시 이동 가능
  - [x] cannons: 각 2개, 상하좌우 n칸, 반드시 기물 하나만 넘어야 이동 가능, 동일한 기물은 불가
  - [x] soldiers: 각 5개, 상좌우 1칸, 막히지 않았을 시 이동 가능

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

움직일 말을 알려주세요.
1 1
도착지를 알려주세요.
1 3

.HER.REHC 1
....G.... 2
CN.....N. 3
S.S.S.S.S 4
......... 5
......... 6
s.s.s.s.s 7
.n.....n. 8
....g.... 9
cehr.rehc 10
123456789

움직일 말을 알려주세요.
9 10

도착지를 알려주세요.
9 8

.HER.REHC 1
....G.... 2
CN.....N. 3
S.S.S.S.S 4
......... 5
......... 6
s.s.s.s.s 7
.n.....nc 8
....g.... 9
cehr.reh. 10
123456789
```

# java-janggi

장기를 구현한다.

# 기물 이동

### 공통

- [x] 팀은 2개이다.
- [x] 판을 벗어나는 움직임은 허용되지 않는다.
- [x] Path 검사 (toX, toY, fromX, fromY)
    - [x] 가려는 곳에 같은 팀이 있으면 못 간다.
    - [x] 가려는 곳에 다른 팀이 있으면 먹는다.

### 기물 특징

- [ ] 궁(GOONG)
    - [x] 상하좌우 한칸씩만 움직일 수 있다.
    - [x] 궁이 잡히면 진다.
    - [ ] 궁은 궁성에서 벗어날 수 없다.
- [x] 차(CHA)
    - [x] 상하좌우 무한대로 움직일 수 있다.
    - [x] 장애물을 뛰어 넘을 수 없다.
- [x] 포(PO)
    - [x] 상하좌우 무한대로 움직일 수 있다.
    - [x] 가는 방향으로 장애물이 하나만 있을 경우 움직일 수 있다.
    - [x] 장애물은 팀에 상관 없이 뛰어 넘을 수 있다.
    - [x] 포끼리는 잡을 수 없다.
    - [x] 포는 뛰어 넘을 수 없다.
- [x] 마(MA)
    - [x] 상하좌우 한칸 + 대각선 한칸씩 움직일 수 있다.
    - [x] 일보 전진하는 곳에 장애물이 있으면 못 간다.
- [x] 상(SANG)
    - [x] 상하좌우 한칸 + 대각선 두칸씩 움직일 수 있다.
    - [x] 일보 전진하는 곳과 대각선 한칸에 장애물이 있으면 못 간다.
- [ ] 사(SA)
    - [x] 상하좌우 한칸씩만 움직일 수 있다.
    - [ ] 사는 궁성에서 벗어날 수 없다.
- [x] 졸(JOL)
    - [x] 상좌우 한칸씩만 움직일 수 있다.
    - [x] 뒤로는 움직일 수 없다.

# 좌표

- [x] 가로(x)
    - [x] 왼쪽에서부터 오른쪽 순서로 표기한다.
    - [x] 가로(x) 좌표가 1 미만일 수 없다.
    - [x] 가로(x) 좌표가 9 초과일 수 없다.
- [x] 세로(y)
    - [x] 위에서 아래쪽 순서로 표기한다.
    - [x] 세로(y) 좌표가 1 미만일 수 없다.
    - [x] 세로(y) 좌표가 10 초과일 수 없다.

# 보드

- [x] 한 팀은 16개 말을 가진다.
    - [x] 궁: 1개
    - [x] 차: 2개
    - [x] 포: 2개
    - [x] 마: 2개
    - [x] 상: 2개
    - [x] 사: 2개
    - [x] 졸: 5개
- [x] 마와 상의 위치는 처음 초기화할 때 선택할 수 있다.
    - [x] 상마상마
    - [x] 마상마상
    - [x] 마상상마
    - [x] 상마마상

```
   １　２　３　４　５　６　７　８　９
 1 차　마　상　사　＿　사　상　마　차　
 2 ＿　＿　＿　＿　궁　＿　＿　＿　＿　
 3 ＿　포　＿　＿　＿　＿　＿　포　＿　
 4 졸　＿　졸　＿　졸　＿　졸　＿　졸　
 5 ＿　＿　＿　＿　＿　＿　＿　＿　＿　
 6 ＿　＿　＿　＿　＿　＿　＿　＿　＿　
 7 졸　＿　졸　＿　졸　＿　졸　＿　졸　
 8 ＿　포　＿　＿　＿　＿　＿　포　＿　
 9 ＿　＿　＿　＿　궁　＿　＿　＿　＿　
10 차　마　상　사　＿　사　상　마　차　
```

# Step2

- [ ] 궁, 사, 궁성 영역 움직임 구현
- [ ] 다른 기물들이 한 턴 이내에 궁을 잡을 수 있는 경우의 수가 생기면 "장군"이라고 알려준다.
- [ ] "장군"의 경우의 수를 회피하면 "멍군"이라고 알려준다.
- [ ] "장군"을 했는데 "멍군"을 할 수 없는 경우 "외통수"라고 알려주고 게임이 끝난다.
- [ ] 예외가 터지면 재입력 받는 로직을 추가한다.
- [ ] 상대 팀 말이면 움직이지 못하게 예외 처리한다.
- [ ] DB를 연결하여 이전에 하려던 게임을 다시 시작할 수 있게 한다.
- [ ] 승패를 점수 계산하는 로직을 추가한다.
- [ ] 게임방을 만들고, 게임방에 입장할 수 있는 기능을 추가한다.

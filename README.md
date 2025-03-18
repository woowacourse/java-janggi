# java-janggi

장기를 구현한다.

### 기물 이동

- [ ] 궁(상하좌우 한칸) GOONG
    - [ ] 궁이 잡히면 진다.
    - [ ] 궁은 궁성에서 벗어날 수 없다.
- [ ] 차(상하좌우 무한대) CHA
    - [ ] 장애물을 뛰어 넘을 수 없다.
- [ ] 포(상하좌우 한놈 잡아서 점프) PO
    - [ ] 앞에 하나가 있어야 뛰어 넘는다.
    - [ ] 내 것, 상대 것 상관 없이 뛰어 넘을 수 있다.
    - [ ] 포끼리는 못 잡는다.
    - [ ] 포를 뛰어 넘을 수 없다.
- [ ] 마(상하좌우 한칸 + 대각선 한칸) MA
    - [ ] 일보 전진하는 곳에 장애물이 있으면 못 간다.
- [ ] 상(상하좌우 한칸 + 대각선 두칸) SANG
    - [ ] 일보 전진하는 곳과 대각선 한칸에 장애물이 있으면 못 간다.
- [ ] 사(상하좌우 한칸) SA
    - [ ] 사는 궁성에서 벗어날 수 없다.
- [ ] 졸(상좌우 한칸) JOL
    - [ ] 뒤로는 움직일 수 없다.

### 좌표

- [x] 가로(x) 1 ~ 9
    - [x] 왼쪽에서부터 오른쪽 순서로 표기한다.
    - [x] 가로(x) 좌표가 9을 초과할 수 없다.
- [x] 세로(y) 1 ~ 10
    - [x] 위에서 아래쪽 순서로 표기한다.
    - [x] 세로(y) 좌표가 10을 초과할 수 없다.
- [x] 음수 좌표는 존재하지 않는다.
- [x] 추가할 가로 거리만큼 변화한 좌표 객체를 반환한다.
- [x] 추가할 세로 거리만큼 변화한 좌표 객체를 반환한다.

### 보드

보드판 (9x10)

- [ ] Map<좌표, 기물>
- [ ] 보드는 처음에 초기화할 때 선택지를 가질 수 있다.
    - [ ] 상마상마
    - [ ] 마상마상
    - [ ] 마상상마
    - [ ] 상마마상

```
차 ? ? 사 x 사 ? ? 차
x x x x 궁 x x x x
x 포 x x x x x 포 x
졸 x 졸 x 졸 x 졸 x 졸
x x x x x x x x x
x x x x x x x x x
졸 x 졸 x 졸 x 졸 x 졸
x 포 x x x x x 포 x
x x x x 궁 x x x x
차 ? ? 사 x 사 ? ? 차
```

### 규칙

- [ ] 한 팀은 16개 말을 가진다.
    - [ ] 궁: 1개
    - [ ] 차: 2개
    - [ ] 포: 2개
    - [ ] 마: 2개
    - [ ] 상: 2개
    - [ ] 사: 2개
    - [ ] 졸: 5개
- [x] 팀은 2개가 있다.
    - [x] 한나라(HAN 팀)은 상대적으로 반대 방향이기 때문에 거리를 반대로 적용한다.
    - [x] 초나라(CHO 팀)은 상대적으로 고정 방향이기 때문에 거리를 그대로 적용한다.
- [ ] 다른 기물들이 한 턴 이내에 궁을 잡을 수 있는 경우의 수가 생기면 "장군"이라고 알려준다.
- [ ] "장군"의 경우의 수를 회피하면 "멍군"이라고 알려준다.
- [ ] "장군"을 했는데 "멍군"을 할 수 없는 경우 "외통수"라고 알려주고 게임이 끝난다.

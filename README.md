# java-janggi

장기 미션 저장소

# 요구 사항

- [x] x와 y 좌표를 가지고 있는 점을 생성한다.
    - [x] x 좌표는 0부터 8까지의 범위를 가진다.
    - [x] y 좌표는 0부터 9까지의 범위를 가진다.
    - [x] 범위를 벗어난 점을 생성할 경우 예외 처리한다.
    - [x] x와 y의 정해진 범위에 맞게 점을 생성 해둔다.


- [x] 기물은 존재 하는 좌표 내에서 움직일 수 있다.
- [x] 기물은 기본 적으로 다른 기물을 뛰어 넘을 수 없다.
- [x] 기물은 같은 나라의 기물이 있는 위치로 이동할 수 없다.
- [ ] 기물이 다른 나라의 기물이 있는 위치로 이동 한다면 해당 기물을 잡을 수 있다.


- [x] 기물은 차(Chariot), 상(Elephant), 마(Horse), 사(Advisor), 장(General), 포(Cannon), 병(Pawn)이 존재한다.
- [x] 기물마다 다른 이동 전략을 가진다.
    - [x] 차는 칸수 제한 없이 직선으로만 이동한다.
    - [x] 상은 상/하/좌/우로 한칸, 한쪽 대각선 방향으로 2칸 이동 한다.
    - [x] 마는 상/하/좌/우로 한칸, 한쪽 대각선 방향으로 1칸 이동 한다.
    - [x] 사는 상/하/좌/우로 한칸 이동 한다.
    - [x] 장은 상/하/좌/우로 한칸 이동 한다.
    - [x] 포는 칸수 제한 없이 직선으로만 이동한다.
        - [x] 이동 경로에 말이 1개 존재해야 한다.
        - [x] 포끼리는 잡을 수도 없고 뛰어 넘을 수도 없다.
    - [x] 병은 상대방을 바라보는 기준에서 상/좌/우 1칸씩만 이동할 수 있다.


- [x] 각각의 기물은 나라를 가진다.
    - [x] 나라는 한나라(Han)와 초나라(Cho)로 나뉜다.


- [x] 초기 기물을 아래와 같이 배치 한다.
    - [x] 한나라의 말을 배치 한다.
    - [x] 초나라의 말을 배치 한다.

```배치 예시
9  차 상 마 사 ＿ 사 마 상 차
8  ＿ ＿ ＿ ＿ 장 ＿ ＿ ＿ ＿
7  ＿ 포 ＿ ＿ ＿ ＿ ＿ 포 ＿
6  병 ＿ 병 ＿ 병 ＿ 병 ＿ 병
5  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
4  ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
3  병 ＿ 병 ＿ 병 ＿ 병 ＿ 병
2  ＿ 포 ＿ ＿ ＿ ＿ ＿ 포 ＿
1  ＿ ＿ ＿ ＿ 장 ＿ ＿ ＿ ＿
0  차 상 마 사 ＿ 사 마 상 차
    0 1 2 3 4 5 6 7 8
```

- [ ] 게임 진행 순서는 초나라부터 시작한다.
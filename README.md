# java-janggi

## 입출력 예시

### 장기판 출력

```aiignore
한: 빨간색
초: 파란색

  0＿1＿2＿3＿4＿5＿6＿7＿8
9 차 상 마 사 ＿ 사 마 상 차
8 ＿ ＿ ＿ ＿ 장 ＿ ＿ ＿ ＿
7 ＿ 포 ＿ ＿ ＿ ＿ ＿ 포 ＿
6 병 ＿ 병 ＿ 병 ＿ 병 ＿ 병
5 ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
4 ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿ ＿
3 병 ＿ 병 ＿ 병 ＿ 병 ＿ 병
2 ＿ 포 ＿ ＿ ＿ ＿ ＿ 포 ＿
1 ＿ ＿ ＿ ＿ 장 ＿ ＿ ＿ ＿
0 차 상 마 사 ＿ 사 마 상 차
```

## 명령어

- `end` : 게임을 종료합니다.
- `move [시작위치] [종료위치]` : 시작위치에서 종료위치로 기물을 이동합니다.
    - `[위치]` : 행번호, 열번호 순으로 입력합니다.

## 게임 시작

- [x] 상차림 번호를 입력받는다
    - 안상(1), 바깥상(2), 왼상(3), 오른상(4)

- [x] 메뉴에 없는 입력을 했을 경우, 재입력을 받는다.
    - `[ERROR] 1~4의 숫자만 입력할 수 있습니다.`

- [x] 상차림 번호에 맞게 보드를 생성한다.

- [x] 현재 보드 상태를 출력한다.

## 게임 턴 관련

- [x] 초부터 턴이 주어진다.
- [x] 초가 턴이 끝나면 한의 턴이 오고, 한 번씩 번갈아가며 턴이 바뀐다.

## 명령어 입력

- [x] `move 00 00` 형식으로 입력을 받는다.
- [x] 형식에 맞지 않는 경우, 재입력을 받는다.
    - `[ERROR] 올바른 형식으로 입력해주세요.`
- [x] 출발 위치에 기물이 없는 경우, 재입력을 받는다.
    - `[ERROR] 00 위치에 기물이 없습니다.`
- [x] 도착 위치로 이동할 수 없는 경우, 재입력을 받는다.
    - `[ERROR] 00 위치로 이동할 수 없습니다.`
- [x] `end`를 입력하면 종료한다.

### 병 이동

- [x] 앞으로 한 칸, 옆으로 한 칸만 이동할 수 있다.

### 마 이동

- [x] 직선으로 한 칸, 대각선으로 한 칸 이동할 수 있다.

### 상 이동

- [x] 직선으로 한 칸, 대각선으로 두 칸 이동할 수 있다.

### 차 이동

- [x] 직선으로 이동할 수 있다.

### 포 이동

- [x] 포를 제외한 기물 하나를 뛰어넘는다.
- [x] 포는 상대 포를 먹을 수 없다.

## 장, 사 이동

- [x] 직선으로 한 칸만 이동할 수 있다.


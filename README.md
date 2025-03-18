# java-janggi

장기 미션 저장소

## 보드판

- [x] 가로는 9줄, 세로는 10줄로 이루어져 있다.
- [x] 마와 상을 제외한 장기 기물의 초기 위치를 저장한다.
- [ ] 모든 말들의 위치를 알고있다.
- [ ] 보드판 밖을 벗어날 수 없다.

## 장기의 두 진영

- [ ] 궁 1개, 사 2개, 마 2개, 상 2개, 졸/병 5개, 포 2개, 차 2개로 구성되어 있다.
- [ ] 한나라, 초나라로 구분된다.
- [ ] 상차림을 정할 수 있다. 한나라 먼저 선택한다.
    - [ ] 왼상차림 상마상마
    - [ ] 오른상차림 마상마상
    - [ ] 안상차림 마상상마
    - [ ] 바깥상차림 상마마상

## 장기 기물

- [ ] 각 기물은 규칙에 따라 움직일 수 있다.
- [ ] 포를 제외한 기물은 다른 기물을 뛰어넘을 수 없다.
- [ ] 각자의 생존 여부를 알고있다.
- [ ] 다른 기물을 잡을 수 있다.

### 궁

- [x] 상, 하, 좌, 우로 한 칸 움직일 수 있다.
- [ ] 궁이 잡히면 게임이 종료된다.

### 사

- [x] 상, 하, 좌, 우로 한 칸 움직일 수 있다.
- [ ] 3점이다.

### 마

- [x] 상, 하, 좌, 우로 한 칸 이동한 후, 대각선으로 한 칸 이동할 수 있다.
- [ ] 5점이다.

### 상

- [ ] 상, 하, 좌, 우로 한 칸 이동한 후, 대각선으로 두 칸 이동할 수 있다.
- [ ] 3점이다.

### 졸, 병

- [x] 상, 좌, 우로 한 칸 이동할 수 있다.
- [x] 초나라와 한나라일 때 상을 이동하는 방법을 구별한다.
- [ ] 2점이다.

### 포

- [ ] 기물 하나를 뛰어 넘어야만 수직, 수평으로 이동할 수 있다.
    - [ ] 포는 포를 먹을 수 없다.
- [ ] 7점이다.

### 차

- [x] 수직, 수평으로 이동할 수 있다.
- [ ] 13점이다.

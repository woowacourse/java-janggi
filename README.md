# java-janggi

> 장기의 보드판 그리고 각 기물을 구현합니다.

## 구현할 기능 목록

- [ ] 게임을 시작한다.
  - [ ] `게임을 시작하시겠습니까? (y/n)` 문구를 출력한다.
    - n을 입력하는 경우 종료된다.
- [ ] 보드판 초기화

## 게임 규칙

- 빨간색이 한, 초록색이 초
- 초부터 시작한다.

## 보드판

- [ ] 9 X 10 사이즈이다.
- [ ] 기물들의 좌표를 관리한다.
  - [ ] 어떤 기물인지는 모른다.
  - [ ] 기물은 영역 밖으로 넘어갈 수 없다.

## 기물

- [ ] 능력을 가진다.
- [ ] 서로 협력한다.
- [x] 진영을 가진다.
  - 초, 한으로 구성된다.

### 마

- [x] 진영을 가진다.
- [x] 마는 직선으로 한 칸, 대각선으로 한 칸 움직일 수 있다.
  - [x] 아닐 경우 예외가 발생한다.

### 상

- [x] 진영을 가진다.
- [x] 상은 직선으로 한 칸, 대각선으로 두 칸 움직일 수 있다.
  - [x] 아닐 경우 예외가 발생한다.

### 차

- [x] 진영을 가진다.
- [x] 차는 상하좌우 무제한으로 움직일 수 있다. 
  - [x] 아닐 경우 예외가 발생한다.

### 군인

- [x] 진영을 가진다.
  - 초 진영은 졸로 표현한다.
  - 한 진영은 병으로 표현한다.
- [x] 병은 앞 또는 양 옆으로 한 칸만 움직일 수 있다.
  - [x] 뒤로 움직일 경우 예외가 발생한다.

## 보드판 초기화 객체

- [ ] 특정 좌표에 특정 기물을 올려둔다.

## 좌표

- [x] x와 y를 가진다.
- [x] 비교할 수 있다.
  - [x] 같은 수평선상에 있는지 확인한다.
  - [x] 같은 수직선상에 있는지 확인한다.
  - [x] 같은 좌표에 있는지 확인한다.
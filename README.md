# java-janggi

## 프로그램 실행 흐름

### 게임 시작

- 게임 시작 안내 메시지를 출력한다.

```
장기 게임에 오신 것을 환영합니다.
```

### 상마 순서 입력

- 상마 순서 결정을 위한 입력을 받는다.

```
초나라 상마 순서를 입력해주세요.
1. 상마상마
2. 상마마상
3. 마상상마
4. 마상마상
1

한나라 상마 순서를 입력해주세요.
1. 상마상마
2. 상마마상
3. 마상상마
4. 마상마상
2
```

- 예외
  - 올바르지 않은 입력이 들어온 경우 에러 메시지를 출력한다.

```
초나라 상마 순서를 입력해주세요.
1. 상마상마
2. 상마마상
3. 마상상마
4. 마상마상
6
[ERROR] 올바른 상마 순서를 입력해주세요.
```

### 기물 위치 초기화

- 입력한 상마 순서에 따라 기물 위치를 초기화한다.

### 이동 커맨드 입력

- move src dst 양식으로 입력해야 한다.
- src에 있는 내 기물을 dst로 이동한다.

```
현재 턴 : 초나라
이동할 기물의 현재 위치와 이동할 위치를 입력해주세요. (예: move 1,1 2,1)
move 1,1 2,1
```

- 예외
  - src가 빈 칸인 경우 예외가 발생한다.
  - src에 상대 기물이 있는 경우 예외가 발생한다.
  - src에서 dst로 이동할 수 없는 경우 예외가 발생한다.

### 보드 출력

- 현재 장기판 상태를 출력한다.
- 팀은 색깔로 구분한다.
  - 초나라 : 초록색
  - 한나라 : 빨간색

```
차 마 상 사 ㅁ 사 상 마 차
ㅁ ㅁ ㅁ ㅁ 왕 ㅁ ㅁ ㅁ ㅁ
ㅁ 포 ㅁ ㅁ ㅁ ㅁ ㅁ 포 ㅁ
병 ㅁ 병 ㅁ 병 ㅁ 병 ㅁ 병
ㅁ ㅁ ㅁ ㅁ ㅁ ㅁ ㅁ ㅁ ㅁ
ㅁ ㅁ ㅁ ㅁ ㅁ ㅁ ㅁ ㅁ ㅁ
병 ㅁ 병 ㅁ 병 ㅁ 병 ㅁ 병
ㅁ 포 ㅁ ㅁ ㅁ ㅁ ㅁ 포 ㅁ
ㅁ ㅁ ㅁ ㅁ 왕 ㅁ ㅁ ㅁ ㅁ
차 마 상 사 ㅁ 사 상 마 차
```

### 승패 결과 출력

- 이동 중간에 왕이 죽으면 승패 결과를 출력한다.
```
초나라의 승리입니다.
```
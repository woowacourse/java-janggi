# java-janggi

장기 미션 저장소

# 장기 기물
- [x] 기물들은 각 움직일 수 있는 규칙이 있다.
- [x] 규칙에 의해 해당 좌표까지 움직일 수 있다.
- [x] 움직일 수 있는 좌표에 아군 기물이 있다면 해당 좌표로 갈 수 없다.

### 병
- [x] 왼쪽, 오른쪽, 앞쪽으로 한 칸 움직일 수 있다.
- [x] 뒤(후퇴)로는 할 수 없다.
- [x] 끝에 도달하면 좌우로만 움직일 수 있다.

### 상
- [x] 앞으로 한 칸 이동 후 대각선 방향으로 두 칸 이동한다.
- [x] 이동하는 경로에 기물이 있다면 움직일 수 없다.

### 포
- [x] 포는 포끼리 잡을 수 없다.
- [x] 중간에 포가 아닌 다른 기물이 있어야 그 방향으로 이동 가능하다.

### 마
- [x] 앞으로 한 칸 이동 후 대각선 방향 한 칸 이동
- [x] 이동 경로에 기물이 있으면 움직일 수 없다.

### 차
- [x] 가로 세로로 끝까지 갈 수 있다.
- [x] 이동 경로에 기물이 있으면 움직일 수 없다.

### 사
- [ ] 궁성 안에서만 움직일 수 있다.
- [x] 상하좌우, 대각선으로 한 칸 움직일 수 있다.

### 궁
- [ ] 궁성 안에서만 움직일 수 있다.
- [x] 상하좌우, 대각선으로 한 칸 움직일 수 있다.

### 장기판
- [x] 장기 기물들을 지정된 좌표에 배치한다.
- [x] 기물들은 장기판 밖으로 나갈 수 없다.
- [x] 장기판은 9 x 10으로 구성된다.
- [x] 가로열은 1번부터 9번까지 왼쪽에서 오른쪽으로 증가한다.
- [x] 세로열은 1번부터 10번까지 위에서 아래로 증가한다.
- [x] 움직일 수 없는 기물은 선택할 수 없다.
- [x] 기물은 지정된 좌표로만 움직일 수 있다.

# 출력 사항
- [x] 각 나라에 맞추어서 색깔을 표현한다.
- [x] 빈 칸은 #으로 출력하도록 한다.
- [x] 각 기물에 맞는 한글로 표현한다.
- [x] 보드판에 좌표를 표시한다.

# 기능 사항

- [x] 보드판을 먼저 출력한다.
  - [x] 먼저 해야할 나라를 출력한다.
    - [x] 이때 시작은 초나라부터 시작한다.
      ```
      [안내] 초나라의 차례입니다.
      ```

- [x] 움직일 기물을 입력받는다.

    ```
    [안내] 기물의 좌표를 '세로,가로' 순으로 입력해주세요. (예: 3,5)
    ```

- [x] 다음과 같은 경우 예외를 발생하고 출력한다.
    - [x] 해당 기물이 존재하지 않음
      ```
      [ERROR] 해당 좌표에 기물이 존재하지 않습니다.
      ```
  
    - [x] 다른 나라의 기물
      ```
      [ERROR] 상대의 기물입니다.
      ```
    - [x] 움직이는 경우의 수가 존재하지 않는 기물
      ```
      [ERROR] 움직일 수 없는 기물입니다.
      ```

- [x] 기물을 입력받은 다음에는 움직일 좌표를 입력받는다.

    ```
    [안내] [해당 기물]이 움직일 좌표를 '세로,가로' 순으로 입력해주세요. (예: 3,5)
    ```

  - [x] 다음과 같은 경우에 예외가 발생된다.
      - [x] 움직일 수 없는 좌표
          ```
          [ERROR] 해당 좌표로 움직일 수 없습니다!
          ```

- [x] 기물이 움직였다면 다음 차례로 넘겨준다.
    ```
    [안내] 한나라의 차례입니다.
    ```
- [x] 상대의 궁이 죽는다면 게임이 종료된다.

    ```
    [안내] 한나라가 이겼습니다!
    ```
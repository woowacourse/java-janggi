# 장기 미션 저장소

## 용어 정리 및 모델링

### 장기판 (Board)

| 용어                      | 설명                                                         |
|-------------------------|------------------------------------------------------------|
| Board                   | 기물이 놓이는 곳. Map<Position, Piece>의 형태로 관리된다.                 |
| Horse Elephant Position | 말과 상의 위치는 게임시작 시 정할 수 있다. 마상마상, 마상상마, 상마상마, 상마마상 중 하나의 배치. |

### 기물 (Piece)

| 용어         | 설명                                                                |
|------------|-------------------------------------------------------------------|
| Piece      | 고유한 이동규칙을 갖는 게임 객체.                                               |
| Piece Type | 기물의 타입. General, Chariot, Cannon, Horse, Elephant, Guard, Soldier |
| Points     | 기물의 타입별로 가지는 고유한 점수.                                              |
| General    | 왕. 잡는 순간 게임은 끝                                                    |
| Chariot    | 차. 13점                                                            |
| Cannon     | 포. 7점                                                             |
| Horse      | 마. 5점                                                             |
| Elephant   | 상. 3점                                                             |
| Guard      | 사. 3점                                                             |
| Soldier    | 졸. 2점                                                             |
| Deom       | 후공 나라에게 1.5점(덤)을 준다. (0.5점은 동점을 방지하기 위해서이다.)                      |

### 이동 규칙 (Move Strategy)

| 용어                     | 설명                                                               |
|------------------------|------------------------------------------------------------------|
| Move Strategy          | 각 기물 별로 가지는 이동 규칙.                                               |
| Cannon Move Strategy   | 직선으로 이동한다. 다른 기물을 단 하나만 넘어야 한다. 포 끼리는 넘을 수 없고 잡을 수 없다.           |
| Chariot Move Strategy  | 직선으로 이동한다. 최대로 다른 기물을 만날 때까지 이동할 수 있다.                           |
| Horse Move Strategy    | 직선으로 한 칸, 대각선으로 한 칸 이동한다. 이동한 방향 기준으로 양쪽 방향으로만 대각선 한 칸 이동할 수 있다. |
| Elephant Move Strategy | 직선으로 한 칸, 대각선으로 두 칸 이동한다. 이동한 방향 기준으로 양쪽 방향으로만 대각선 두 칸 이동할 수 있다. |
| General Move Strategy  | 모든 방향으로 한 칸 이동한다. 궁성에서만 이동 가능하다.                                 |
| Guard Move Strategy    | 모든 방향으로 한 칸 이동한다. 궁성에서만 이동 가능하다.                                 |
| Soldier Move Strategy  | 뒤를 제외한 모든 방향으로 한 칸 이동한다.                                         |

### 위치 (Position)

| 용어       | 설명                                            |
|----------|-----------------------------------------------|
| Position | 장기판의 좌표. 행과 열로 관리된다.                          |
| Row      | 행. 1~10                                       |
| Column   | 열. 1~9                                        |
| Palace   | 궁성. 장기판 위의 3*3 크기의 구역이고, 장과 사는 이곳에만 이동할 수 있다. |

### 나라 (Dynasty)

| 용어      | 설명         |
|---------|------------|
| Dynasty | 기물이 속하는 진영 |
| HAN     | 한나라        |
| CHO     | 초나라        |

### 방향 (Direction)

| 용어        | 설명      |
|-----------|---------|
| Direction | 기물의 방향. |

### 장기 게임 (Game)

| 용어   | 설명                                 |
|------|------------------------------------|
| Game | 장기판 초기화와 기물 이동을 담당하는 게임 객체         |
| Turn | 두 나라 중 어떤 나라가 기물을 움직이는 차례인지 나타내는 턴 |

## 기능 요구 사항

### 1.1단계 - 보드 초기화

- [x] 게임 시작 시 Board과 전체 Piece을 올바른 Position에 초기화한다.
    - [x] Horses와 Elephants의 Position은 각 Dynasty에게 입력받도록한다.

### 1.2단계 - 기물 이동 가능 지역 파악

- [x] 선택된 Piece가 갈 수 있는 Position들을 파악한다.
    - Position들은 각 Piece의 Move Strategy를 기반으로 계산된다.

### 1.3단계 - 기물 이동

- [x] 각 Piece의 Move Strategy를 구현한다.
- [x] Piece의 Move Strategy은 직접 요구사항을 분석하여 정의한다.
    - [x] General: Palace 내부에서만 1칸 자유롭게 이동 가능.
    - [x] Guard: Palace 내부에서만 1칸 자유롭게 이동 가능.
    - [x] Chariot: 동일한 Row 또는 동일한 Column에 위치한 곳으로 이동 가능.
        - [x] 궁성 내부에서는 대각선으로 이동 가능
    - [x] Cannon: 동일한 Row 또는 동일한 Column에 위치한 곳으로 이동 가능.
        - 꼭 기물 하나를 넘어야 함
        - Cannon 끼리는 넘을 수 없음
        - Cannon 끼리는 먹을 수 없음
        - [x] 궁성 내부에서는 대각선으로 이동 가능
    - [x] Horse: Row 또는 Column으로 1칸 이동하고 진행한 방향으로 오른쪽(or왼쪽) 대각선 1칸 이동
    - [x] Elephant: Row 또는 Column으로 1칸 이동하고 진행한 방향으로 오른쪽(or왼쪽) 대각선 2칸 이동
    - [x] Soldier: 앞으로 또는 좌우로 1칸 이동 가능
        - 궁성 내부에서는 대각선으로 이동 가능

### 2.1단계 - 기물의 확장

- [x] Palace 영역의 규칙을 포함하여 Piece의 Move Strategy를 구현한다.
- [x] 장기는 General이 잡히는 경우 게임에서 진다. General이 잡혔을 때 게임을 종료해야 한다.

### 2.2단계 - 승패 처리

#### 장기 대국에서 무승부를 허용하지 않는다.

- [x] 무승부가 허용되지않기 때문에 아래와 같은 상황에서 각 Dynasty의 Piece Points를 합산하여 승부를 결정한다.
    - ~~무한 장군과 멍군, 동일한 국면 반복~~
    - [x] 위 경우를 판단하기 어렵다면 두 Dynasty가 30점 미만이 된 순간 점수를 비교하여 승패를 결정한다.
- [x] 따라서 각 Dynasty마다 현재 남아 있는 Piece에 대한 Points를 구할 수 있어야 한다.
    - [x] Chariots는 13point이다.
    - [x] Cannons는 7point이다.
    - [x] Horses는 5point이다.
    - [x] Elephants는 3point이다.
    - [x] Guards는 3point이다.
    - [x] Soldiers는 2point이다.
- [x] CHO Dynasty는 선공이므로 유리하다. 불리한 HAN Dynasty에게는 Deom을 부여한다.
    - 따라서 게임 시작 시 각 Dynasty의 Points는 다음과 같다.
    - CHO: 72점
    - HAN: 73.5점

### 2.3단계 - DB 적용

- [x] Application을 재시작하더라도 이전에 하던 장기를 다시 시작할 수 있어야 한다.
- [x] DB를 적용할 때 도메인 객체의 변경을 최소화해야 한다.

## 게임 흐름 세부 조건 사항

### 게임 준비 (차림)

- [x] 각 Dynasty의 Horse와 Elephant의 배치를 입력받는다.
    - [x] 차림은 숫자로 입력받는다.
        - 1: Horse-Elephant-Horse-Elephant
        - 2: Horse-Elephant-Elephant-Horse
        - 3: Elephant-Horse-Elephant-Horse
        - 4: Elephant-Horse-Horse-Elephant
- [x] Board에 Piece를 올바른 Position에 배치한다.

### 게임 시작 (기물 이동 및 게임 종료 조건)

- [x] 움직이고 싶은 Piece의 Position을 입력받는다.
- [x] 선택된 Position에 있는 Piece가 도착할 수 있는 Positions를 출력한다.
    - [x] 도착할 수 없는 Position을 입력하면 오류를 일으킨다.

- [x] General이 잡힐 때까지 위 과정을 Dynasty가 Turn을 반복한다.
    - [x] Turn의 시작은 CHO부터 한다.
    - [x] General이 사라진 Dynasty가 있으면 종료한다.

### 게임 종료 (종료 및 승패 판단)

- 승패 판단은 아래와 같은 두 가지 방식으로 진행된다.
    - [x] General이 잡힌 Dynasty는 패배처리 된다. (반대 Dynasty는 승리한다.)
    - [x] 또는 두 Dynasty가 30점 미만이 된 순간 점수를 비교하여 승패를 결정한다.

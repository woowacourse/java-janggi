# 🀄️장기

## 📚 용어 정리 및 모델링

### 장기 게임(Game)
| 용어          | 설명                    |
|-------------|-----------------------|
| Game        | 장기 게임의 흐름을 처리.        |
| CurrentTurn | 현재 기물을 움직일 수 있는 나라 정보 |

### 장기판 (Board)

| 용어    | 설명                                         |
|-------|--------------------------------------------|
| Board | 기물이 놓이는 곳. Map<Position, Piece>의 형태로 관리된다. |

### 기물 (Piece)

| 용어         | 설명                                                                                      |
|------------|-----------------------------------------------------------------------------------------|
| Piece      | 고유한 이동규칙을 갖는 게임 객체.                                                                     |
| Piece Type | 기물의 유형.  CANNON(포), CHARIOT(차), ELEPHANT(상), GENERAL(장), GUARD(사), HORSE(마), SOLDIER(졸) |

### 이동 규칙 (Move Strategy)

| 용어            | 설명         |
|---------------|------------|
| Move Strategy | 기물의 이동 규칙. |

### 위치 (Position)

| 용어       | 설명                   |
|----------|----------------------|
| Position | 장기판의 좌표. 행과 열로 관리된다. |
| Row      | 행. 1~10              |
| Column   | 열. 1~9               |

### 방향 (Direction)

| 용어        | 설명                   |
|-----------|----------------------|
| Direction | 기물이 움직일 수 있는 8가지 방향. |

### 나라 (Dynasty)

| 용어      | 설명         |
|---------|------------|
| Dynasty | 기물이 속하는 진영 |

## 📃 기능 요구 사항

### 1.1단계 - 보드 초기화

- [x] 게임 시작 시 Board과 전체 Piece을 올바른 Position에 초기화한다.
    - [x] Horses와 Elephants의 Position은 각 Dynasty에게 입력받도록한다.
    - [x] 1.1단계에서는 기물의 이동은 구현하지 않는다.

### 1.2단계 - 기물 이동 가능 지역 파악

- [x] 선택된 Piece가 갈 수 있는 Position들을 파악한다.
    - Position들은 각 Piece의 Move Strategy를 기반으로 계산된다.

### 1.3단계 - 기물 이동

- [x] 각 Piece의 Move Strategy를 구현한다.
- [ ] Piece의 Move Strategy은 직접 요구사항을 분석하여 정의한다.
    - [ ] General : 궁성 내부에서 1칸 자유롭게 이동 가능.
    - [ ] Guard : 궁성 내부에서 1칸 자유롭게 이동 가능.
    - [x] Chariot : 동일한 Row 또는 동일한 Column에 위치한 곳으로 이동 가능.
        - [ ] 궁성 내부에서는 대각선으로 이동 가능
    - [x] Cannon : 동일한 Row 또는 동일한 Column에 위치한 곳으로 이동 가능.
        - 꼭 기물 하나를 넘어야 함
        - Cannon 끼리는 넘을 수 없음
        - Cannon 끼리는 먹을 수 없음
        - [ ]궁성 내부에서는 대각선으로 이동 가능
    - [x] Horse : Row 또는 Column으로 1칸 이동하고 진행한 방향으로 오른쪽(or왼쪽) 대각선 1칸 이동
    - [x] Elephant : Row 또는 Column으로 1칸 이동하고 진행한 방향으로 오른쪽(or왼쪽) 대각선 2칸 이동
    - [x] Soldier : 앞으로 또는 좌우로 1칸 이동 가능
        - [ ] 궁성 내부에서는 대각선으로 이동 가능

- [ ] 궁성(宮城) 영역은 구현하지 않는다. (사이클2에서 다룬다)

## ✏️ 게임 흐름 세부 조건 사항

### 게임 준비 (차림)

- [x] 각 Dynasty의 Horse와 Elephant의 배치를 입력받는다.
    - [x] 차림은 숫자로 입력받는다.
      - 1: Horse-Elephant-Horse-Elephant
      - 2: Horse-Elephant-Elephant-Horse
      - 3: Elephant-Horse-Elephant-Horse
      - 4: Elephant-Horse-Horse-Elephant
    - [x] 다음의 상황의 경우 예외가 발생한다:
      - 숫자가 아닌 입력 값이 들어오는 경우
      - 1~4중 하나가 아닌 경우
- [x] Board에 Piece를 올바른 Position에 초기화한다.
- [x] 초기화된 Board를 출력한다.

### 게임 시작 (2단계)
- [x] 각 Dynasty가 턴을 번갈아가며 게임을 한다.
- [x] 현재 턴인 Dynasty의 해당하는 Piece를 하나 선택한다.
  - [x] 선택할 Piece의 현재 좌표로 입력 받는다. (예: 2,1)  
  - [x] 다음의 상황의 경우 예외가 발생한다:
    - [x] 입력값이 좌표형식이 아닌 경우
    - [x] 선택된 Piece가 움직일 수 있는 Position이 없는 경우
    - [x] 상대 팀의 Piece를 선택한 경우
- [x] 선택된 Piece가 움직일 수 있는 Position을 제공받는다.
- [x] Piece가 움직일 Position을 선택한다.
  - [x] 움직일 Position은 좌표로 입력 받는다. (예: 2,1)
  - [x] 다음의 상황의 경우 예외가 발생한다:
      - [x] 입력값이 좌표형식이 아닌 경우
      - [x] 움직일 수 없는 Position으로 이동하려는 경우
- [x] Piece를 이동시킨 후 Board를 출력한다.

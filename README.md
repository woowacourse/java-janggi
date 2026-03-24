# 장기 미션 저장소

## 용어 정리 및 모델링

### 장기판 (Board)

| 용어    | 설명                                         |
|-------|--------------------------------------------|
| Board | 기물이 놓이는 곳. Map<Position, Piece>의 형태로 관리된다. |

### 기물 (Piece)

| 용어         | 설명                                                                     |
|------------|------------------------------------------------------------------------|
| Piece      | 고유한 이동규칙을 갖는 게임 객체.                                                    |
| Piece Name | 기물이 가지는 이름.  general, chariot, cannon, horse, elephant, guard, soldier |

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

### 나라 (Dynasty)

| 용어      | 설명         |
|---------|------------|
| Dynasty | 기물이 속하는 진영 |

## 기능 요구 사항

### 1.1단계 - 보드 초기화

- [ ] 게임 시작 시 Board과 전체 Piece을 올바른 Position에 초기화한다.
    - [ ] Horses와 Elephants의 Position은 각 Dynasty에게 입력받도록한다.
    - [ ] 1.1단계에서는 기물의 이동은 구현하지 않는다.

### 1.2단계 - 기물 이동

- [ ] 각 Piece의 Move Strategy를 구현한다.
- [ ] Piece의 Move Strategy은 직접 요구사항을 분석하여 정의한다.
    - [ ] General : 궁성 내부에서 1칸 자유롭게 이동 가능.
    - [ ] Guard : 궁성 내부에서 1칸 자유롭게 이동 가능.
    - [ ] Chariot : 동일한 Row 또는 동일한 Column에 위치한 곳으로 이동 가능.
        - 궁성 내부에서는 대각선으로 이동 가능
    - [ ] Cannon : 동일한 Row 또는 동일한 Column에 위치한 곳으로 이동 가능.
        - 꼭 기물 하나를 넘어야 함
        - Cannon 끼리는 넘을 수 없음
        - Cannon 끼리는 먹을 수 없음
        - 궁성 내부에서는 대각선으로 이동 가능
    - [ ] Horse : Row 또는 Column으로 1칸 이동하고 진행한 방향으로 오른쪽(or왼쪽) 대각선 1칸 이동
    - [ ] Elephant : Row 또는 Column으로 1칸 이동하고 진행한 방향으로 오른쪽(or왼쪽) 대각선 2칸 이동
    - [ ] Soldier : 앞으로 또는 좌우로 1칸 이동 가능
        - 궁성 내부에서는 대각선으로 이동 가능

- [ ] 궁성(宮城) 영역은 구현하지 않는다. (사이클2에서 다룬다)

## 게임 흐름 세부 조건 사항

### 게임 준비 (차림)

- [ ] 각 Dynasty의 Horse와 Elephant의 배치를 입력받는다.
    - [ ] 차림은 숫자로 입력받는다.
        - 1: Horse-Elephant-Horse-Elephant
        - 2: Horse-Elephant-Elephant-Horse
        - 3: Elephant-Horse-Elephant-Horse
        - 4: Elephant-Horse-Horse-Elephant
- [ ] Board에 Piece를 올바른 Position에 배치한다.

### 게임 시작 (2단계)

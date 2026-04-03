# java-janggi

장기 미션 저장소

## 시퀀스
- 컨트롤러 생성 및 컨트롤러 run 한다
- InputView에게 초기 배치 입력을 요청한다
    - 사용자에게 초기 배치 입력을 받는다
- 초기 배치 입력을 바탕으로 장기 게임 생성한다
    - 생성시 Factory을 사용해서 Board를 생성한다
        - 시용자에게 초기 배치 입력을 받은 값을 바탕으로 전략을 생성하고 Board를 반환한다
- 장기판을 출력한다
- while로 2명이 돌아가면서 게임을 진행한다
    - 움직일 기물의 좌표 입력을 요청하고 받는다
        - 플래그를 통해서 다른 나라 말을 건들이지 않도록 한다
    - 기물이 움직일 좌표 입력을 요청하고 받는다
        - 움직일 수 있는지 검증한다
    - 기물을 움직인다
    - flag를 수정한다
    - 장기판을 출력한다


## 기능 목록

### Application
- 컨트롤러 생성 및 컨트롤러 run 한다

### InputView
- 사용자에게 초기 배치 입력을 받는다
- 사용자에게 움직일 기물의 좌표 입력을 받는다
- 사용자에게 기물이 움직일 좌표 입력을 받는다

### OutputView
- 장기판을 출력한다

### Controller
- 책임: 입출력 및 게임 흐름 제어한다
- InputView에게 초기 배치 입력을 요청한다
- 초기 배치 입력을 바탕으로 장기 게임 생성한다
- while로 2명이 돌아가면서 게임을 진행한다

### JanggiGame
- 책임 : 게임 진행 간에 도메인들과의 협력을 돕는다
- 생성시 Factory을 사용해서 Board를 생성한다
- 플래그를 통해서 다른 나라 말을 건들이지 않도록 한다
- flag를 수정한다

### Board
- 책임 : 위치를 가지고 기물을 이동시킨다
- 기물을 움직인다

### BoardFactory
- 책임 : 초기 세팅에 맞는 장기판 생성을 한다
- 시용자에게 초기 배치 입력을 받은 값을 바탕으로 전략을 생성하고 Board를 반환한다

### PlacementOption(Enum)
- 책임 : 사용자 입력값과 전략을 연결한다
- DEFAULT("1", new DefaultPlacement()),
- INNER_HORSE("2", new InnerHorsePlacement()),
- LEFT_INNER_HORSE("3", new LeftInnerHorsePlacement()),
- RIGHT_INNER_HORSE("4", new RightInnerHorsePlacement());

### Strategy
- 책임 : 4가지 경우의 수에 맞춰 초기 장기판 세팅을 반환한다

### Coordination
- 책임 : 좌표 값을 검증 및 관리한다

### Piece
- 책임 : 룰을 가지고 움직일 수 있는지를 판단한다
- 움직일 수 있는지 검증한다

### MoveRule
- 책임: 기물의 이동 규칙을 가진다
-
### Team(Enum)
- 책임 : 진영이 맞는지 판단한다
- CHO(0, 초)
- HAN(1, 한)

### NameStorage(Enum)
- 책임 : 출력 형식에 맞춰준다
- 전달받은 class명을 바탕으로 한글 출력 및 색깔 형식으로 치환한다

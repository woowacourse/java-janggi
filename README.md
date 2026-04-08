# java-janggi

장기 미션 저장소

# 기능

## 1-1 요구사항

- [x] 각 플레이어 진형 입력 기능
- [x] 장기판 초기화 기능
- [x] 장기판 상태 출력 기능

## 1-2 요구사항

- [x] 이동할 기물 위치 좌표 입력 기능
- [x] 이동할 목적지 좌표 입력 기능
- [x] 턴제 시스템 기능
- [x] 장기 진행 상태 출력 기능
- [x] 기물 이동 기능

## 2-1 요구사항

- [x] 기물 궁성 영역 이동 기능
- [x] 기물 점수 계산 기능
- [x] 게임 결과 출력 기능

## 2-2 요구사항

- [x] 장기 게임 상태 저장 기능
- [x] 장기 게임 불러오기 기능

## ⚠️ 기능 예외 사항

### 입력 검증

- 포진 선택: 1~4 범위 외 입력 시 예외
- 좌표 범위: x(1~9), y(1~10) 범위 외 입력 시 예외

### 기물 선택 검증

- 선택한 위치에 기물이 존재하지 않을 경우 예외
- 선택한 기물이 아군 기물이 아닐 경우 예외

### 이동 규칙 검증

- 목적지가 기물의 이동 규칙에 맞지 않을 경우 예외
- 목적지에 아군 기물이 존재할 경우 예외
- 이동 경로에 기물이 존재할 경우 예외 (포 제외)

### 포 규칙 검증

- 이동 경로에 포가 존재할 경우 예외
- 이동 경로에 기물이 정확히 1개가 아닐 경우 예외
- 포로 포를 잡으려 할 경우 예외

---

## 📁 프로젝트 구조

```
src/main/java
├── Main.java
├── config
│   └── AppConfig.java
├── console
│   └── GameConsole.java
├── constant
│   └── BoardSpec.java
├── domain
│   ├── board
│   │   ├── Board.java
│   │   ├── BoardFactory.java
│   │   └── Formation.java
│   ├── game
│   │   ├── Game.java
│   │   └── Turn.java
│   ├── movement
│   │   ├── LinearMovement.java
│   │   ├── Movement.java
│   │   └── PathMovement.java
│   ├── path
│   │   ├── Direction.java
│   │   ├── ElephantPaths.java
│   │   ├── HoresePaths.java
│   │   ├── LinearPiecePaths.java
│   │   ├── PalacePiecePaths.java
│   │   ├── Path.java
│   │   ├── Paths.java
│   │   └── SoliderPaths.java
│   ├── piece
│   │   ├── Cannon.java
│   │   ├── Chariot.java
│   │   ├── Elephant.java
│   │   ├── Empty.java
│   │   ├── Guard.java
│   │   ├── Horse.java
│   │   ├── King.java
│   │   ├── Piece.java
│   │   ├── PieceType.java
│   │   ├── Side.java
│   │   └── Soldier.java
│   ├── strategy
│   │   ├── LinearMovementStrategy.java
│   │   ├── PathMovementStrategy.java
│   │   └── PieceMoveStrategy.java
│   └── vo
│       ├── LoadGameDecision.java
│       └── Position.java
├── repository
│   ├── connector
│   │   ├── Connector.java
│   │   └── MysqlConnector.java
│   ├── game_record
│   │   ├── GameRecordRepository.java
│   │   ├── GameRecordRepositoryImpl.java
│   │   └── dto
│   │       └── GameRecord.java
│   └── move_record
│       ├── MoveRecordRepository.java
│       ├── MoveRecordRepositoryImpl.java
│       └── dto
│           └── MoveRecord.java
├── service
│   └── GameService.java
├── util
│   └── Parser.java
└── view
    ├── InputView.java
    └── OutputView.java

```
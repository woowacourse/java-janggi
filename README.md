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

## 🐳 Docker Compose 실행

### 방법 1. 앱과 MySQL을 함께 Docker로 실행

아래 명령으로 앱 컨테이너와 MySQL 컨테이너를 함께 실행할 수 있습니다.
`app` 서비스에는 DB 환경변수가 Docker Compose에서 자동으로 주입되므로 별도 `export`가 필요하지 않습니다.

```bash
docker compose run --rm --build app
```

앱 컨테이너 내부에서 사용되는 DB 설정은 아래와 같습니다.

- URL: `jdbc:mysql://mysql:3306/JANGGI?serverTimezone=Asia/Seoul`
- USER: `janggi`
- PASSWORD: `janggi1234`

### 방법 2. MySQL만 Docker로 실행하고 앱은 로컬에서 실행

먼저 MySQL 컨테이너만 실행합니다.

```bash
docker compose up -d mysql
```

상태를 확인하려면 아래 명령을 사용합니다.

```bash
docker compose ps
```

로컬 JVM에서 실행할 때는 `DatabaseProperties`가 환경변수만 읽으므로 아래 값을 먼저 설정해야 합니다.

```bash
export JANGGI_DB_URL='jdbc:mysql://127.0.0.1:13306/JANGGI?serverTimezone=Asia/Seoul'
export JANGGI_DB_USER='janggi'
export JANGGI_DB_PASSWORD='janggi1234'
```

IntelliJ를 사용하면 Run Configuration의 `Environment variables`에 같은 값을 넣으면 됩니다.

그 다음 IntelliJ에서 `Main`을 실행하면 됩니다.

### 빠른 정리

- 앱과 MySQL을 모두 Docker로 실행: `docker compose run --rm --build app`
- MySQL만 Docker로 실행: `docker compose up -d mysql`
- MySQL 상태 확인: `docker compose ps`

### 종료 및 초기화

MySQL 컨테이너를 내리려면 아래 명령을 사용합니다.

```bash
docker compose down
```

최초 컨테이너 생성 시 [docker/mysql/init.sql](/Users/yeoli/git/java-janggi/docker/mysql/init.sql)가 실행되어 `game_record`, `move_record` 테이블을 생성합니다.

기존 볼륨까지 지우고 처음부터 다시 만들려면 아래 명령을 사용합니다.

```bash
docker compose down -v
```

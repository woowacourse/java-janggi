# java-janggi

장기 미션 저장소

---

## 기능 목록

### 1단계 - 기본 기능

#### 1-1 요구사항
- [x] 각 플레이어 진형 입력 기능
- [x] 장기판 초기화 기능
- [x] 장기판 상태 출력 기능

#### 1-2 요구사항
- [x] 이동할 기물 위치 좌표 입력 기능
- [x] 이동할 목적지 좌표 입력 기능
- [x] 턴제 시스템 기능
- [x] 장기 진행 상태 출력 기능
- [x] 기물 이동 기능

### 2단계 - 기물 확장 + DB 적용

#### 2-1 요구사항 (기물 확장)
- [x] 궁성 영역 대각선 이동 규칙 구현
- [x] 왕 캡처 시 게임 종료 기능
- [x] 기물 점수 계산 기능
  - 차(13), 포(7), 마(5), 상(3), 사(3), 졸/병(2)
  - (한) 덤 1.5점

#### 2-2 요구사항 (DB 적용)
- [x] 게임 저장 기능 (MongoDB)
- [x] 게임 불러오기 기능
- [x] 새 게임 / 이어하기 선택 기능
- [x] 게임 삭제 기능
- [x] 존재하지 않는 게임 ID 입력 시 재선택 기능

---

## 예외 사항

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

## 프로젝트 구조

```
src/main/java
├── Main.java
├── JanggiRunner.java
├── constant
│   ├── BoardConstant.java
│   └── ErrorMessage.java
├── domain
│   ├── Direction.java
│   ├── Formation.java
│   ├── Game.java
│   ├── Palace.java
│   ├── Position.java
│   ├── ScoreStatus.java
│   ├── Side.java
│   ├── Turn.java
│   ├── TurnAction.java
│   ├── piece
│   │   ├── Cannon.java
│   │   ├── Chariot.java
│   │   ├── Elephant.java
│   │   ├── Empty.java
│   │   ├── Guard.java
│   │   ├── Horse.java
│   │   ├── King.java
│   │   ├── Piece.java
│   │   ├── PieceType.java
│   │   └── Soldier.java
│   └── strategy
│       ├── LinearMovement.java
│       ├── MovementStrategy.java
│       └── PathMovement.java
├── repository
│   ├── GameRepository.java
│   ├── GameStatus.java
│   └── MongoDBRepository.java
└── view
    ├── ConsoleView.java
    ├── InputView.java
    └── OutputView.java
```

## MongoDB 설정

### macOS
```bash
brew tap mongodb/brew
brew install mongodb-community
brew services start mongodb-community
```

### Windows
1. [MongoDB 공식 사이트](https://www.mongodb.com/try/download/community)에서 MSI 설치 파일 다운로드
2. 설치 시 "Install MongoDB as a Service" 체크
3. 설치 완료 후 자동 실행

### Docker
```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

### 연결 정보
- URI: `mongodb://localhost:27017`
- Database: `janggi_db`
- Collections: `boards`, `counters`

---
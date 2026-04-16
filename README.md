# java-janggi

장기 미션 저장소

### 기능 요구사항 체크리스트

#### step 1
- [x] 10x9 장기판 좌표를 `Point(y, x)`로 표현하고, 범위를 벗어난 좌표 생성 시 예외를 발생시킨다.
- [x] 기물 타입(`PieceType`)과 진영(`Team`)을 분리해 도메인 모델링한다.
- [x] 빈 칸을 `NonePiece`로 포장해 `Intersection.empty()`로 관리한다.
- [x] 장기판 초기화 시 전체 좌표를 빈 칸으로 채운 뒤, 생성기(`IntersectionGenerator`)가 전달한 기물을 배치한다.
- [x] 초/한 기본 배치(졸, 포, 장군, 사, 차)를 자동 생성한다.
- [x] 상/마 차림(`Formation`) 4가지를 지원하고, 진영별로 대칭 배치한다.
- [x] `Formation.valueOf(int)`로 입력 숫자를 차림 enum으로 변환한다.
- [x] 보드에서 좌표로 교차점(`Intersection`)을 조회할 수 있다.
- [x] 이동 규칙을 `MoveRule` 전략으로 분리하고, 기물 타입별 규칙 클래스를 연결한다.
- [x] 이동 경로를 `Direction`/`Directions`로 추상화해 “도착 가능 여부 + 경유 좌표”를 계산한다.
- [x] 차(`Chariot`)는 상하좌우 직선 이동만 가능하며, 경로 중간 장애물이 있으면 이동할 수 없다.
- [x] 마(`Horse`)는 2단계 경로(멱) 중 중간 칸이 막히면 이동할 수 없다.
- [x] 상(`Elephant`)은 3단계 경로 중 중간 칸이 막히면 이동할 수 없다.
- [x] 포(`Cannon`)는 정확히 1개의 장애물을 넘어야 하며, 포를 넘거나 포를 공격할 수 없다.
- [x] 장군(`General`)과 사(`Guard`)는 1칸 상하좌우 이동을 지원한다.
- [x] 졸(`Soldier`)은 좌/우 + 전진 이동을 지원하며, 진영에 따라 전진 방향이 다르다.
- [x] 같은 팀 기물이 있는 칸으로는 이동할 수 없다.
- [x] `JanggiBoard.tryToMove()` 수행 시 도착지는 출발 기물로 갱신되고, 출발지는 빈 칸이 된다.

#### step 2
- [x] `Point`에서 궁성(`isPalace`)·궁성 대각 허용 칸(`isPalaceDiagonal`)을 판별한다.
- [x] `Directions.merge`로 이동 방향 집합을 합쳐, 궁성 안 직선·대각 경로를 조합한다.
- [x] `MoveRule`이 출발/목적 칸에 따라 `makeDirections`로 이동 가능 방향을 동적으로 만든다.
- [x] 장군·사는 궁성 안에서만 이동하며, 대각 칸끼리는 1칸 대각 이동을 추가로 허용한다.
- [x] 차·포는 출발·목적이 모두 궁 대각 칸일 때 대각 직선 이동을 기존 직선 이동에 합친다.
- [x] 졸은 진영별 기본 이동에 더해, 궁 대각 칸에서는 팀에 맞는 대각 한 칸 이동을 추가한다.
- [x] MySQL에 `game`·`piece` 테이블을 두고, classpath의 `jdbc.properties`와 `DriverManager`로 연결을 열어 `ConnectionFactory`로 감싼다.
- [x] 신규 게임 시 `game` 행과 초기 `piece` 행을 한 트랜잭션으로 저장하고, 생성된 `game id`를 사용한다.
- [x] 매 수마다 DB의 `piece id`(좌표 기준 조회)로 이동·포획을 반영하고 `game` 정보(차례·점수·진행·승자)를 갱신한다.
- [x] 미종료 게임 목록을 보여 주고, 선택 시 DB에서 로드해 `Game`·보드를 복원한다.
- [x] 도메인 `Game`·`Piece`는 persistence 식별자를 갖지 않으며, id 생성·보관은 DB가 담당한다.

### 주요 로직 요약

#### step 1
- **보드 생성**
    - `JanggiBoard`가 10x9 전체 좌표를 `Intersection.empty()`로 초기화한다.
- **초기 기물 배치**
    - `JanggiGenerator`가 진영(`CHO`, `HAN`)별 기본 행/열 규칙으로 기물을 만든다.
    - 상/마는 `Formation`에 정의된 열 인덱스 조합으로 배치한다.
- **좌표/교차점 모델링**
    - `Point`는 생성 시 범위 검증을 수행하고, `next(Vector)`로 이동 좌표를 계산한다.
    - `Intersection`은 기물 도착(`arrive`)과 이탈(`leave`) 책임을 가진다.
- **이동 규칙 선택**
    - 보드는 출발 지점의 기물 타입을 기준으로 `MoveRule` 구현체를 선택한다.
- **경로 계산**
    - 규칙별 `Directions.findPoints(from, to)`로 목적지까지의 경유 좌표 목록을 만든다.
- **규칙 검증**
    - 공통적으로 같은 팀 도착지 여부를 검증한다.
    - 차/마/상은 경로 장애물 조건을 검증한다.
    - 포는 “장애물 1개 필수”, “포 넘기/포 공격 금지”를 추가 검증한다.
- **실제 이동 반영**
    - 검증 통과 시 도착지 `arrive(from)`, 출발지 `leave()` 순서로 상태를 갱신한다.

#### step 2
- **궁성 이동**
    - `Point`가 궁 3×3과 “대각 한 칸이 허용되는 궁 안 칸”을 구분한다.
    - 규칙 클래스는 `defaultDirection`(기존 직선·곡선 패턴)과 `palaceDirection`(궁 전용)을 필요 시 `merge`한다.
    - 장·사는 `findPossiblePoints` 단계에서 출발/목적이 모두 궁 안일 때만 후보를 계산하고, 궁 밖이면 예외로 막는다.
- **DB 저장**
    - `Application`이 `DatabaseConfig.createConnectionFactory()`로 연결 팩토리를 만들고, `TransactionTemplate`·`GameDao`·`JanggiController`를 조립한다.
    - 트랜잭션 경계는 `TransactionTemplate`이 담당하고, `GameDao`는 전달받은 `Connection`으로 SQL만 실행한다.
    - `game`: 진행 여부·차례·양 팀 점수·승자(null이면 재개 가능)·타임스탬프.
    - `piece`: `game_id` FK, 팀·기물 종류·좌표; 게임 삭제 시 CASCADE.
    - 컨트롤러는 `game id`를 유지하고, 이동 전 DB에서 `piece id`를 조회한 뒤 `TransactionTemplate.executeInTransaction` 안에서 `GameDao.persistMove(conn, MovePersistDto)`로 반영한다.
    - 재개 시 `LoadedGameState`·`LoadedPiece`로 읽어 `SavedPiecesGenerator`·`Game.restored`로 메모리 상태를 맞춘다.

### 기물별 이동 규칙 정리

- **차(`Chariot`)**
    - 상/하/좌/우 직선 다칸 이동
    - 도착지 아군 금지
    - 경로 중간 장애물 금지
    - (step 2) 출발·목적이 모두 궁 대각 칸이면 대각 방향 직선 이동을 직선 이동에 합친다.
- **포(`Cannon`)**
    - 상/하/좌/우 직선 이동
    - 중간 장애물 정확히 1개 필요
    - 장애물/도착지가 포인 경우 금지
    - 도착지 아군 금지
    - (step 2) 출발·목적이 모두 궁 대각 칸이면 대각 직선 패턴을 기존 직선 패턴에 합친다.
- **마(`Horse`)**
    - 1칸 직선 + 1칸 대각(총 2스텝)
    - 중간 경유 칸 장애물 금지
    - 도착지 아군 금지
- **상(`Elephant`)**
    - 1칸 직선 + 2칸 대각(총 3스텝)
    - 경유 칸 장애물 금지
    - 도착지 아군 금지
- **장군/사(`General`/`Guard`)**
    - 1칸 상하좌우 이동
    - 도착지 아군 금지
    - (step 2) 궁성 밖으로는 이동할 수 없고, 궁 안 대각 칸끼리는 1칸 대각 이동이 추가된다.
- **졸(`Soldier`)**
    - 좌/우 + 전진 이동
    - CHO는 위쪽(UP), HAN은 아래쪽(DOWN) 전진
    - 도착지 아군 금지
    - (step 2) 궁 대각 칸에서는 진영에 따라 대각 한 칸 이동이 추가된다.

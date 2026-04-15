## Step2 - 장기 게임 기능 목록

### 2.1 게임 규칙 완성

#### 궁성 이동

- [x] 궁, 사는 궁성 내에서 대각선 1칸 이동이 가능하다
- [x] 차, 포는 궁성 내에서 대각선 직선 이동이 가능하다
- [x] 병/졸은 궁성 내에서 전진 방향 대각선 1칸 이동이 가능하다

#### 점수

- [x] 현재 남아 있는 기물로 팀별 점수를 계산한다
    - 차: 13, 포: 7, 마: 5, 사: 3, 상: 3, 병: 2, 궁: 0
- [x] 한(HAN) 팀은 후수 보너스 1.5점이 추가된다

#### 게임 종료 조건

- [x] 궁이 잡히면 잡은 쪽이 승리한다
- [x] 빅장 (양측 궁이 같은 열에서 사이에 기물 없이 마주봄) 시 점수로 승패를 결정한다
- [x] 양측 연속 한 수 쉬기 시 점수로 승패를 결정한다
- [x] 점수가 동일하면 무승부로 처리한다
- [x] 게임 종료 시 양측 점수와 승자를 출력한다
- [x] 한 수 쉬기(pass) 기능

### 2.2 게임방 및 영속화

#### 게임방

- [x] 게임방을 생성할 수 있다
- [x] 게임방에 이름을 지정할 수 있다
- [x] 게임방 목록을 조회할 수 있다
- [x] 게임방을 선택하여 입장할 수 있다
- [x] 진행 중인 게임방과 종료된 게임방을 구분할 수 있다

#### DB 영속화

- [x] 게임방 정보를 저장하고 조회할 수 있다
- [x] 보드 상태(기물 배치)를 저장하고 복원할 수 있다
- [x] 턴 정보를 저장하고 복원할 수 있다
- [x] 게임 종료 시 결과(승자, 점수)를 저장한다
- [x] 애플리케이션을 재시작하더라도 이전에 하던 게임을 이어서 할 수 있다

---
## 테이블 구조

### `game_room`
진행 중이거나 종료된 게임방의 메타 정보를 보관한다.

| 컬럼                       | 타입           | 설명                               |
|--------------------------|--------------|----------------------------------|
| `id`                     | BIGINT (PK)  | 게임방 식별자 (AUTO_INCREMENT)         |
| `name`                   | VARCHAR(255) | 게임방 이름                           |
| `current_turn`           | VARCHAR(10)  | 현재 턴 (`CHO` / `HAN`), 기본값 `CHO`  |
| `status`                 | VARCHAR(10)  | 게임 상태 (`RUNNING` / `FINISHED` 등) |
| `consecutive_pass_count` | INT          | 연속 한 수 쉬기 횟수                     |
| `created_at`             | TIMESTAMP    | 생성 시각                            |

### `board_piece`
각 게임방의 보드 위 기물 배치를 저장한다. `game_room.id`를 FK로 참조한다.

| 컬럼             | 타입          | 설명                       |
|----------------|-------------|--------------------------|
| `id`           | BIGINT (PK) | 행 식별자 (AUTO_INCREMENT)   |
| `game_room_id` | BIGINT (FK) | 소속 게임방 id                |
| `row_pos`      | INT         | 기물의 행 위치                 |
| `col_pos`      | INT         | 기물의 열 위치                 |
| `piece_type`   | VARCHAR(20) | 기물 종류 (궁/차/포/마/상/사/병/졸) |
| `team`         | VARCHAR(10) | 소속 팀 (`CHO` / `HAN`)     |

---
## Step2에서 추가된 주요 클래스

### 도메인 (`domain`)
- `game/GameStatus` — 게임 진행 상태(`RUNNING`, `FINISHED` 등)를 표현하는 enum. 게임방 status 영속화에 사용된다.
- `game/GameResult` — 승자/무승부 여부, 양측 점수 등 게임 종료 결과를 캡슐화한다.
- `game/GameRecord` — 게임 종료 조건 검사 결과와 최종 `GameResult`를 묶어 컨트롤러/뷰에 전달한다.
- `game/ScoreCalculator` — 남은 기물로 팀별 점수를 계산하고 한(HAN) 팀 후수 보너스(+1.5)를 적용한다.
- `game/condition/GameEndCondition` — 게임 종료 조건을 나타내는 공통 인터페이스.
- `game/condition/GeneralCapturedCondition` — 궁이 잡힌 경우의 승패 판정.
- `game/condition/BikjangCondition` — 빅장(양측 궁이 열을 공유하고 사이에 기물 없음) 판정.
- `game/condition/ConsecutivePassCondition` — 양측 연속 한 수 쉬기 종료 판정.
- `position/Palace` — 궁성 영역을 표현하고 기물이 궁성 내부에 있는지 판정한다.
- `rule/MoveRule` 및 구현체들 — 기물별 기하학적 이동 규칙을 추상화. `StraightLineRule`, `LShapeRule`, `ExtendedLShapeRule`, `ForwardAndSideRule`, `PalaceOrthogonalRule`, `PalaceDiagonalForwardRule`, `PalaceDiagonalStraightRule`, `PalaceDiagonalOneStepRule` 등이 있다.
- `board/formation/*` — 진형(원앙마/양귀마/좌귀마/우귀마) 팩토리를 `formation` 패키지로 분리하여 응집도를 높였다.

### 컨트롤러 (`controller`)
- `command/TurnCommand` — 한 턴 동안 수행할 동작을 나타내는 sealed 인터페이스. 이동/한수쉬기 분기를 캡슐화한다.
- `command/MoveCommand` — 기물 이동 커맨드. 출발/도착 좌표를 들고 `JanggiGameService.move`를 호출한다.
- `command/PassCommand` — 한 수 쉬기 커맨드. `JanggiGameService.pass`를 호출한다.

### 서비스 & 저장소 (`service`, `repository`, `dao`)
- `service/JanggiGameService` — 게임 생성·입장·이동·한수쉬기 유스케이스를 제공하며, 도메인 변경 후 저장까지 한 트랜잭션처럼 묶어 처리한다.
- `repository/GameRepository` — `JanggiGame` 도메인과 DAO 사이의 경계를 담당. 게임 저장·복원·목록 조회를 제공한다.
- `dao/GameRoomDao` — `game_room` 테이블에 대한 CRUD 담당.
- `dao/BoardPieceDao` — `board_piece` 테이블에 대한 CRUD 담당.
- `dao/GameRoomRawData` — `game_room` 레코드에 대한 raw DTO.
- `dao/BoardPieceRawData` — `board_piece` 레코드에 대한 raw DTO.

### DB 인프라 (`db`)
- `db/ConnectionManager` — `application.properties`를 읽어 MySQL 커넥션을 생성·제공한다.
- `db/DatabaseInitializer` — 애플리케이션 기동 시 `schema.sql`을 실행해 테이블을 초기화한다.
- `db/PieceTypeMapper` — 도메인 기물 클래스와 DB에 저장되는 `piece_type` 문자열 간 매핑을 담당.

### 뷰 (`view`)
- `view/MainMenu` — 메인 메뉴 선택지(새 게임/이어하기/종료)를 표현하는 enum.

---
## 환경 설정
### MySql 설치 및 실행 (Docker) 
```
docker run -d \
--name janggi-mysql \
-e MYSQL_ROOT_PASSWORD=password \
-e MYSQL_DATABASE=janggi_db \
-p 3306:3306 \
-v $(pwd)/mysql_data:/var/lib/mysql \
mysql:8.0
```

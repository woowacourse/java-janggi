# java-janggi

장기 미션 저장소

## 기능 목록

### Application
- 애플리케이션을 실행한다.
- `JanggiController`를 생성하고 게임을 시작한다.

### Controller
- 초/한 진영의 마상 배치 입력을 받는다.
- 입력값으로 장기 게임을 생성한다.
- 턴 단위로 입력, 이동, 출력 흐름을 제어한다.
- 왕이 잡힐 때까지 게임을 진행한다.

### InputView
- 초/한 진영의 초기 배치 옵션을 입력받는다.
- 움직일 기물의 좌표를 입력받는다.
- 이동할 목적지 좌표를 입력받는다.

### OutputView
- 현재 장기판을 출력한다.
- 각 진영의 현재 점수를 출력한다.
- 현재 턴을 출력한다.
- 게임 종료 시 승리 진영을 출력한다.

### JanggiGame
- 장기판 생성 책임을 `BoardFactory`에 위임한다.
- 현재 턴을 관리한다.
- 현재 턴의 진영만 선택할 수 있도록 검증을 요청한다.
- 기물 이동을 진행한다.
- 왕이 잡히면 게임 종료 상태를 반환한다.
- 각 진영의 남은 기물 점수를 조회한다.

### Board
- 좌표별 기물 상태를 관리한다.
- 기물 이동 결과를 반영한다.
- 살아 있는 왕의 수를 기준으로 게임 종료 여부를 판단한다.
- 각 진영의 남은 기물 점수를 계산한다.

### BoardFactory
- 빈 장기판을 생성한다.
- 초/한 진영의 배치 전략을 적용해 초기 보드를 생성한다.

### Palace
- 궁성 범위를 관리한다.
- 궁/사의 궁성 이동 가능 여부를 판단한다.
- 차/포의 궁성 대각선 경로를 제공한다.
- 병/졸의 궁성 대각선 전진 가능 여부를 판단한다.

### PlacementInputMapper
- 사용자 입력값을 `FormationType`으로 변환한다.

### PlacementStrategy
- 진영별 초기 배치 공통 로직을 제공한다.
- 마/상 배치 방식에 따라 서로 다른 초기 배치를 구성한다.

### Coordination
- 좌표 생성과 범위 검증을 담당한다.
- 두 좌표 사이의 차이와 경로를 계산한다.
- 특정 범위 안에 포함되는지 판단한다.

### Piece
- 모든 기물의 공통 이동 검증 흐름을 제공한다.
- 이동 규칙, 경로, 경로 위 장애물 검증을 각 기물에게 위임한다.
- 기물 점수를 제공한다.

### Piece 구현체
- `General`: 궁성 내부 한 칸 직선/대각선 이동을 처리한다.
- `Guard`: 궁성 내부 한 칸 직선/대각선 이동을 처리한다.
- `Soldier`: 전진/좌우 이동과 적 궁성 내부 대각선 전진 이동을 처리한다.
- `Chariot`: 직선 이동과 궁성 대각선 이동을 처리한다.
- `Cannon`: 직선 이동과 궁성 대각선 이동을 처리한다.
- `Horse`: 말의 이동과 멱 규칙을 처리한다.
- `Elephant`: 상의 이동과 멱 규칙을 처리한다.
- `EmptyPiece`: 빈 칸을 표현한다.

### Turn
- 현재 차례의 진영을 관리한다.
- 턴 전환을 처리한다.
- 선택한 기물이 현재 차례의 진영인지 검증한다.

### Team
- 초/한/빈 진영을 표현한다.

## DB 실행

- MySQL은 [`docker-compose.yml`](/Users/jeongjaemin/우테코_8기/Lv_1/java-janggi/docker-compose.yml)로 실행한다.
- 스키마 생성은 [`sql/init.sql`](/Users/jeongjaemin/우테코_8기/Lv_1/java-janggi/sql/init.sql)에서 담당한다.
- 애플리케이션은 시작 시 테이블을 생성하지 않는다. MySQL 컨테이너가 먼저 초기화되어 있어야 한다.
- 애플리케이션 접속 정보는 [`src/main/resources/db.properties`](/Users/jeongjaemin/우테코_8기/Lv_1/java-janggi/src/main/resources/db.properties)와 Docker 설정을 동일하게 유지한다.
- 호스트 포트는 `3307`, 컨테이너 내부 포트는 `3306`을 사용한다.
- 현재 기본 접속 정보는 아래와 같다.

```properties
db.url=jdbc:mysql://localhost:3307/janggi?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
db.username=janggi
db.password=1234
```

### 1. DB 컨테이너 실행

```bash
docker compose up -d
```

- 백그라운드에서 MySQL 컨테이너를 실행한다.
- 최초 실행 시 `sql/init.sql`이 자동 실행되어 테이블을 생성한다.

### 2. 컨테이너 상태 확인

```bash
docker compose ps
```

```bash
docker compose logs -f mysql
```

- `docker compose ps`로 컨테이너가 올라왔는지 확인한다.
- `docker compose logs -f mysql`에서 에러 없이 초기화가 끝났는지 확인한다.

### 3. 테이블 생성 확인

```bash
docker compose exec mysql mysql -ujanggi -p1234 janggi -e "SHOW TABLES;"
```

- `games`, `game_pieces` 테이블이 보이면 초기화가 완료된 상태다.

### 4. 애플리케이션 실행

- MySQL 컨테이너가 정상 기동된 뒤 애플리케이션을 실행한다.

### 5. 스키마 변경 반영

```bash
docker compose down -v
```

```bash
docker compose up -d
```

- `/docker-entrypoint-initdb.d`의 SQL은 데이터 볼륨이 비어 있을 때만 실행된다.
- 이미 생성된 `mysql-data` 볼륨이 있으면 `sql/init.sql` 수정 내용이 자동 반영되지 않는다.
- 스키마를 다시 적용하려면 볼륨까지 삭제한 뒤 컨테이너를 다시 실행해야 한다.

### 6. 컨테이너 종료

```bash
docker compose down
```

- 컨테이너만 내리고 데이터는 유지한다.
- 데이터까지 함께 제거하려면 `docker compose down -v`를 사용한다.

## 사이클 2 규칙 정리

### 궁성 이동
- 궁과 사는 궁성 내부에서만 이동할 수 있다.
- 궁과 사는 한 칸 직선 이동이 가능하다.
- 궁과 사는 궁성 대각선으로 한 칸 이동이 가능하다.
- 차는 궁성의 대각선 길을 따라 이동할 수 있다.
- 포는 궁성의 대각선 길을 따라 이동할 수 있다.
- 포는 궁성 대각선 이동 시에도 중간에 정확히 한 개의 기물이 필요하고, 그 기물은 포가 될 수 없다.
- 병/졸은 적 궁성 내부에서만 대각선 전진 이동이 가능하다.

### 게임 종료
- 왕이 잡히면 즉시 게임이 종료된다.
- 게임 종료 시 마지막으로 이동한 진영이 승리한다.

### 점수 계산
- 차: `13점`
- 포: `7점`
- 마: `5점`
- 상: `3점`
- 사: `3점`
- 병/졸: `2점`
- 궁: `0점`

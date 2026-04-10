# java-janggi

## 🚀 사이클 2 - 기물 확장 + DB 적용

- ✔️ 궁성 구현
    - 궁성 위치(PalacePosition) enum 추가
    - 특정 위치에서 대각선 이동
    - 대각선 이동은 궁성 위치 내에서만 가능하도록
    - 대각선 이동과 기본 이동을 각각의 전략으로 구현, 병합
- ✔️ 종료 조건
    - 궁(장) 이 2개가 아닌 순간 게임 종료
- ✔️ 점수 계산
    - 장기 규칙에 맞춰 기물 점수 계산
    - 초 진영 어드벤티지 부여(1.5)
- ✔️ DB 적용
    - H2 사용 - 편의성 고려
    - 서비스 계층 - 저장소 - DAO/DTO
    - 테이블 설계 - 참조관계 설정 - DB 구현 - 도메인 연동

### 🗃️ DB 설계

> ### 초기
>
> (id 는 공통이니 생략)
>
> 게임(생성일시) date
>
> 플레이어(닉네임, 게임id, 진영id) varchar int int
>
> 현재 턴(게임id, 진영 id) int int
>
> 진영(진영) varchar?
>
> 기물(종류) varchar?
>
> 위치(행번호, 열번호) int int
>
> 기물 배치 상태(게임id, 기물id, 위치id) int int int

> ### 수정
> - 도메인과 테이블이 1:1 관계인가?
> - 0~N : 0:N 관계
> - 없어도 될 테이블은 과감히 속성으로 격하하고
    >

- 필수적인 데이터(가변 상태)만 테이블로 묶어 저장

> ```java
> public static Players from(String choPlayerName, String hanPlayerName) {
>     ->varchar cho_player_name, varchar han_player_name
> }
> // 상태와 값(enum) 을 String(varchar)으로 저장 / 로드
> ```

TABLE `GAME` (게임 정보)

    game_id(PK)
    
    cho_player_name(VARCHAR)
    
    han_player_name(VARCHAR)
    
    current_turn[Side] (VARCHAR)
    
    created_at(DATETIME)

    is_finished(boolean)

~~TABLE `PLAYER` (플레이어 정보)~~ 정규화가 뭔가요

    player_id(PK) - 필요한가? 닉네임과 게임ID 조합으로 표현 가능할거같은데 

    player_nickname - 중복 검사해서 생성되겠지만 PK 로 정해두면 교차 검증 가능?

    playing_game_id - 두개 조합으로 관리하는게 

TABLE `BOARD` (기물 배치 상태)

    game_id(FK)
    
    row_index(INT)
    
    col_index(INT)
    
    piece_type[PieceType] (VARCHAR)
    
    side(VARCHAR)
    
    piece_number(VARCHAR)

### DB 에서 뽑아올 정보

- #### 게임(진행 중인) 정보
    - `게임ID` - 몰라도 되지만, 식별할 방법은? 플레이어 닉네임? 중복이라면?
    - `플레이어 닉네임(초)`
    - `플레이어 닉네임(한)`
    - `플레이어 턴`
    - `게임 생성 일시`
    - `게임 종료 일시` - 없으면 게임 종료?
- #### 해당 게임의 기물 배치 정보
    - `기물 종류` - PieceType Enum 이름을 varchar 로
    - `기물 위치` - 기물의 위치 Position row/column 을 int 로

> 게임 ID 는 몰라도 되고, 진행 중인 게임의 목록은 출력  
> 이어갈 게임을 선택`순번으로`하거나, `0 입력`  
> 해당 게임 정보를 불러와서 GameManager 조립해주면 완-벽

### 🛠️ 도메인 수정

- `class Players`
    - 테이블에 초/한 진영의 플레이어를 저장해야 한다.
    - currentPlayer 를 통한 get / playerDTO 를 통한 비교는 가능하나 비즈니스 로직이 아님.
    - > 효율화/비즈니스 로직 충족 일거양득을 위해선 ~~Map 도입~~이?
- ### `getter`
    - getter 를 제거하기 위해 map 을 도입했으나,
    - PieceMapper 는 실제 Piece 클래스의 내부 구조를 그대로 표현.
    - 근본적 목적에 부합한가?
    - > 불변 클래스의 getter 는 나쁜 게터가 아니다  
      불변 상태는 그대로 get 할 수 있되,   
      그 활용은 단순 전달에 한한다.
      >
      > 외부에서 상태를 판단하고 결정하지 않고, 방어적 복사를 보장한다면 getter 허용.

- ✔️ 선택) 게임방 구현

## 1차 PR 코멘트 반영

### `InitialBoardInfo` `Side-Effect` `Pure Function`

- 출력 매개변수(Output Parameter) 패턴. 굳이 매개변수로 받아와서 채울 이유가?
    - 의문 : get 해서 값을 넣기보단 그저 맵을 주고 시키는 것이 `TDA` 하지않나?
    - 반문 : 저게 게터가 맞나?   
      내부의 상태를 꺼내다 쓰는 것이 아닌  
      `요청`한 `행위`의 `결과`를 그저 반환하는 것  
      미션 1 에서의 파생 상태도 아닌 그야말로 `결과`
    - 실행 : `BoardInfo` 는 각 진영별 기물 맵을 리턴, `Board` 는 초기화하며 그 값들을 `putAll`

### `위임에 대한 테스트`

- `gameManager.isFinished()` 는 `board.isBothPalaceExist()` 를 호출하는 단순 위임.
    - 의문 : 단순 호출 체인 전체를 테스트해야 하는가?
    - 반문 : 이번 아무 일도 안하는 catch 문과 동일한 경우라 생각.  
      상위에선 정상 동작하지만 그 내부가 두번 꼬여서 정상 동작하는 거라면?
    - 실행 : 호출 체인 각각을 다 테스트하기
      -
            * 마찬가지로 전략도 다 테스트는했는데 겹치는 부분을 분리하는 것이 가장 바람직할듯

        * `최소 단위 행위에 대한 테스트가 곧 그 상위 행위에 대한 테스트를 대체한다` 를 위해선
        * 중복된 기능에 대한 통합과 그에 대한 테스트가 매우 효과적일 듯
        * 장기 규칙 6번의 `역할/인터페이스 설계 기준` 에 추가/수정이 필요한 부분?

## 📋 목차

- [개요](#-개요)
- [구현 기능 목록](#-구현-기능-목록)
- [입출력 요구 사항](#입출력-요구-사항)
- [커밋 컨벤션](#커밋-컨벤션)

---

## 1️⃣ 개요

### 기능 흐름

```
1-1. 초 진영 플레이어의 이름을 입력받는다.
↓
1-2. 한 진영 플레이어의 이름을 입력받는다.
↓
2. 장기판과 기물을 초기화하고 게임을 시작한다.
↓
┌─→ 3. 현재 턴인 플레이어에게 이동할 기물을 입력받는다.
│            ↓
│   4. 해당 기물이 이동 가능한 경로를 출력한다.
│            ↓
│   5-1. 해당 기물이 이동할 좌표(행)를 입력받는다.
│            ↓
│   5-2. 해당 기물이 이동할 좌표(열)를 입력받는다.
│            ↓
│   6. 해당 이동이 규칙(공통/개별)에 맞는지 검증한다.
│   ⬆️ ↙ [불가능]                       ↘ [가능]
│  (재입력)                     7. 기물을 이동하고 장기판을 갱신한다.
│                                         ↓
│                               6. 상대의 '궁'이 잡혔는지 확인한다.
│                                 ↙ [아니오]          ↘ [예]
└─────────────────────────────────┘             7. 게임을 종료한다.
------------------------2차 구현 및 PR 포인트---------------------------
                                                      ↓
                                                8. 승패 결과를 출력한다.
```

## 2️⃣ 구현 기능 목록 (사이클 1: 보드 초기화 + 기물 이동)

## 1. 게임 준비 및 플레이어 설정

- [x] **[UI]** 선수(초)와 후수(한) 플레이어의 이름을 입력받는다. `class InputView`
    - [x] [출력] 초 진영의 플레이어 이름 입력 안내문 출력 - `class OutputView`
    - [x] [입력] 초 진영의 플레이어 이름 입력 - `public String readPlayerName`
    - [x] [출력] 한 진영의 플레이어 이름 입력 안내문 출력 - `class OutputView`
    - [x] [입력] 한 진영의 플레이어 이름 입력 - `public String readPlayerName`
    - [x] [예외 처리] 입력이 비어있는 경우, `IllegalArgumentException`을 발생시키고 재입력을 받는다.

- [x] **[Domain]** 플레이어가 속할 진영을 관리하는 클래스 `class Side`
    - [x] [규칙] 진영은 초와 한으로 나뉘고, 각각의 이름을 가진다 `private final String displayName`

- [x] **[Domain]** 플레이어를 관리하는 클래스 `class Player`
    - [x] [규칙] 플레이어는 진영을 가지고, 각각의 이름을 가진다.
      `private final Side side` `private final String nickname`

- [x] **[Domain]** 플레이어들을 관리할 일급 컬렉션 `class Players`
    - [x] [규칙] 플레이어들을 관리할 컬렉션을 가진다. `private Set<Player> players`
    - [x] [규칙] 각 진영의 플레이어 이름으로 플레이어 일급 컬렉션 생성 `public from(String choPlayer, String hanPlayer)`
    - [x] [규칙] 각 진영의 플레이어 이름으로 플레이어 객체 생성 `public Player(String name, Side side)`
    - [x] [예외 처리] 플레이어 닉네임이 중복되는 경우, `IllegalArgumentException` 을 발생시킨다.
      `private void validateDuplicatedName(String choPlayerName, String hanPlayerName)`

## 2. 보드 초기화 및 출력

- [x] **[Domain]** 기물의 위치를 관리하는 클래스 `record Position`
    - [x] [규칙] 기물의 행 번호를 표시 `private final int row`
    - [x] [규칙] 기물의 열 번호를 표시 `private final int column`
    - [x] [규칙] 보드의 행 최댓값을 표시 `private final int BOARD_MAX_ROW`
    - [x] [규칙] 보드의 행 최솟값을 표시 `private final int BOARD_MIN_ROW`
    - [x] [규칙] 보드의 열 최댓값을 표시 `private final int BOARD_MAX_COLUMN`
    - [x] [규칙] 보드의 열 최솟값을 표시 `private final int BOARD_MIN_COLUMN`
    - [x] [규칙] 이동할 거리를 받아 이동한 결과를 새로운 객체로 리턴한다 `public Position of(int row, int column)`
    - [x] [예외 처리] 클래스 생성 시, 보드의 행 / 열 범위를 벗어나는 위치면 `IllegalArgumentException` 을 발생시킨다.
      `private void validateBounds(int row, int column)`

- [x] **[Domain]** 기물의 이동 경로를 관리하는 클래스 `class Path`
    - [x] [규칙] 기물의 이동 경로를 위치의 목록으로 표시 `private final List<Position> positions`
    - [x] [규칙] 기물의 위치를 하나씩 받아 이동 경로를 표시 `public void makePath(Position nextPosition)`

- [x] **[Domain]** 기물의 모든 이동 경로를 관리하는 일급 컬렉션 `class Destinations`
    - [x] [규칙] 기물이 이동할 수 있는 모든 경로를 목록으로 표시 `private final List<Path> paths`
    - [x] [규칙] 기물의 이동 경로를 하나씩 받아 모든 이동 경로를 표시 `public void addPath(Path destinations)`

- [x] **[Domain]** 기물의 최소 이동 단위를 관리하는 클래스 `enum Direction`
    - [x] [규칙] 기물의 모든 최소 이동 단위 경우의 수를 정의
        - E(0, 1)
        - W(0, -1)
        - S(1, 0)
        - N(-1, 0)
        - NE(-1, 1)
        - NW(-1, -1)
        - SE(1, 1)
        - SW(1, -1)
    - [x] [규칙] 이동 단위는 초기 위치 `class Positoin` 을 받아, 이동 위치 `class Position` 를 반환한다.
      `public Static Position move(Position currentPosition)`

- [x] **[Domain]** 기물의 이동 규칙(전략)을 관리하는 클래스 `interface MoveStrategy`
    - [x] [규칙] 기물이 이동 가능한 모든 `경로`를 계산해서, 경로의 집합으로 반환  
      `Destinations findMovablePaths(Position current, EnumSet<Direction> directions)`
    - [x] [규칙] 기물이 이동 가능한 모든 `위치`를, 해당 경로에 있는 기물 정보를 반영하고 계산해서 반환  
      `List<Position> determineDestinations(Destinations routes, Map<Position, Piece> boardState, Piece movingPiece)`

- [x] **[Domain]** 기물의 이동 규칙(전략)을 관리하는 구현체 클래스 `class Strategy extends PieceStrategy`
    - [x] [규칙] 각 기물별 이동 규칙을 방향 `enum Direction` 의 조합 `abstract sealed class EnumSet` 으로 표현
    - [x] [규칙] 동일한 이동 규칙 `class StepMoveStrategy` (한 칸 이동)을 가진 경우,  
      동일한 전략 객체를 재사용하되 방향의 조합을 조율해 해당 기물의 이동 규칙을 표현
    - [ ] [의문] `CHARIOT` / `CANNON` 은 동일한 직선 이동 `경로` 를 가졌지만  
      `이동 규칙` 은 다름. 이 때 상속과 재정의를 사용하는 것이 과연 바람직할까?

- [x] **[`Domain/VO`]** 기물의 이동 가능성을 판정하기 위한 최소 정보를 전달할 클래스 `record Piece`
    - [x] [규칙] 기물의 진영을 전달 `Side side`
    - [x] [규칙] 같은 진영인지 판별 `public boolean isAlly(Piece)`
    - [x] [규칙] 기물의 종류를 전달 `PieceType pieceType`
    - [x] [규칙] 기물의 종류를 판별 -> 현재 필요한 건 포 여부 뿐 `public boolean isCannon()`
    - [x] [규칙] 기물의 번호(식별자)를 전달 `PieceType pieceType` - 현재 불필요하지만 추가해서 전달

- [x] **[Domain]** 기물의 종류, 이동 규칙을 관리하는 클래스 `enum PieceType`
    - [x] [규칙] 기물의 종류 `PieceType`
        - General
        - Guard
        - Horse
        - Elephant
        - Chariot
        - Cannon
        - Soldier
    - [x] [규칙] 기물의 이동 규칙 `class Strategy extends PieceStrategy`
        - [x] [규칙] * `1.1단계 - 보드 초기화` 를 선행하기 위해 비워 둠

- [x] **[Domain]** 기물의 정보를 관리하는 클래스 `class Piece`
    - [x] [규칙] 기물의 고유한 식별자 `private final String pieceNumber`
    - [x] [규칙] 기물의 고유한 종류 `private final PieceType pieceType`
    - [x] [규칙] 기물의 소속 진영 `private final Side side`
    - [x] [규칙] 기물이 이동 가능한 모든 `경로`를 계산해서, 경로의 집합으로 반환
      `public Destinations calculatePaths(Position current)`
    - [x] [규칙] 기물이 이동 가능한 모든 `위치`를, 해당 경로에 있는 기물 정보를 반영하고 계산해서 반환
      `public List<Position> determineDestinations(Destinations routes, Map<Position, Piece> boardState)`
    - [x] [규칙] 기물의 상태를 포장된 객체로 반환 `public Piece toVO()`

- [x] **[Domain]** 게임판과 그에 속한 기물, 각 기물의 위치를 관리할 일급 컬렉션 `class Board`
    - [x] [규칙] ※ 요구사항 분석과 미션 진행 설계에 따라 상/마 자유 배치 생략, 기본 위치로 일괄 고정.
    - [x] [규칙] 각 진영에 속한 `졸(병) 포 차 마 상 사 궁` 을 배치한다.
      ![](https://i.namu.wiki/i/j-sZdZbz3kD7bGBzAq8G4Rbkl-gfasbRzB9hFgQp3tqpnfo-cLccIqPqEjiUi30MadlJdqvP-Jkw5NUqhKJBdQ.svg)
        - [x] [규칙] `졸(병)` 은 5개, 각 진영 첫 번째 행 (초-6, 한-3) 양 끝 열 (0, 8) 에서부터, 한 칸의 간격을 두고 배치한다.
          ![](https://velog.velcdn.com/images/nn98/post/9729b224-4b37-4fbe-b08d-9a7148b5fcad/image.png)
        - [x] [규칙] `포` 는 2개, 각 진영 두 번째 행 (초-7, 한-2) 양 끝 열 (0, 8) 에서 한 칸의 간격을 두고 배치한다.
          ![](https://velog.velcdn.com/images/nn98/post/7b636ac0-e9da-4651-8dc3-1c29b272cf68/image.png)
        - [x] [규칙] `차` 는 2개, 각 진영 마지막 행 (초-9, 한-0) 양 끝 열 (0, 8) 에 배치한다.
          ![](https://velog.velcdn.com/images/nn98/post/d80c5ac6-9650-499f-add7-4baa9a8ec491/image.png)
        - [x] [규칙] `상` 은 2개, 각 진영 마지막 행 (초-9, 한-0) 양 끝에서 한 칸 떨어진 열 (1, 7) 에 배치한다.
          ![](https://velog.velcdn.com/images/nn98/post/abe72e90-a785-4b61-a542-caa5c54bde56/image.png)
        - [x] [규칙] `마` 는 2개, 각 진영 마지막 행 (초-9, 한-0) 양 끝에서 두 칸 떨어진 열 (2, 6) 에 배치한다.
          ![](https://velog.velcdn.com/images/nn98/post/43ca2acf-71cf-48e1-80c9-f93d737225e1/image.png)
        - [x] [규칙] `사` 는 2개, 각 진영 마지막 행 (초-9, 한-0) 양 끝에서 세 칸 떨어진 열 (3, 5) 에 배치한다.
          ![](https://velog.velcdn.com/images/nn98/post/8eb3d297-6ba9-4209-9c55-efef637f6bae/image.png)
        - [x] [규칙] `궁` 은 1개, 각 진영 마지막에서 한 칸 윗 행 (초-8, 한-1) 중간 열 (4) 에 배치한다.
        - ![](https://velog.velcdn.com/images/nn98/post/7e935174-b161-4812-9e91-111a3a8c5bca/image.png)

- [x] **[DTO/UI]** 현재 장기판의 상태를 출력한다. `class OutputView`
    - [x] [`!임시`] 장기판 `class Board`의 기물 배치 상태 `Map<Position, Piece> piecePosition`를 바탕으로 `class BoardDTO` 생성
      `public BoardDTO from(Board board)`
    - [x] [`!임시`] `BoardDTO`를 전달받아 장기판의 기물 배치 상태를 콘솔에 출력한다.  
      `public void printBoardStatus(BoardDTO boardDto)`

## 3. 기물 이동 로직

- [x] **[UI]** 현재 턴의 플레이어에게 이동할 기물과 도착 위치를 차례로 입력받는다.
    - [x] [규칙] 게임은 항상 초(초)가 먼저 시작한다.
    - [x] [예외 처리] 보드 밖의 좌표, 빈 공간, 또는 상대방의 기물을 선택한 경우 예외 발생 후 재입력.

- [x] **[Domain]** 공통 이동 규칙을 검증한다.
    - [x] [규칙] 도착 위치에 같은 팀(아군) 기물이 있으면 이동할 수 없다.
    - [ ] [규칙] 이동 이후 자신의 '궁'이 상대에게 잡힐 수 있는 위험한 상태(장군)가 된다면, 그 이동은 허용되지 않는다.

- [x] **[Domain]** 개별 기물의 이동 규칙을 검증한다. (※ **궁성 영역 배제** 룰 적용)
    - [x] [규칙] **궁/사**: (궁성을 구현하지 않으므로) 현재 위치에서 상하좌우 1칸씩만 이동 가능하다.
    - [x] [규칙] **차**: 상하좌우로 거리 제한 없이 이동할 수 있다. 단, 이동 경로 중간에 다른 기물이 있으면 뛰어넘을 수 없다.
    - [x] [규칙] **포**: 이동 경로 사이에 반드시 다른 기물이 하나 이상 있어야 하며, 넘는 기물이 '포'이면 안 된다. 또한 도착 위치의 기물이 '포'인 경우에는 잡을 수 없다.
    - [x] [규칙] **마**: 직진 한 칸 후 대각선 한 칸 이동한다. 직진하는 첫 칸에 기물이 있으면 이동할 수 없다 (멱).
    - [x] [규칙] **상**: 직진 한 칸 후 대각선으로 두 칸 이동한다. 이동 경로(직선 1칸, 대각선 1칸)가 막혀 있으면 이동할 수 없다 (멱).
    - [x] [규칙] **졸/병**: 한 칸씩 전진 및 좌우 이동만 가능하다. (후퇴 불가)

- [x] **[UI]** 선택한 기물이 이동할 수 있는 유효한 위치 목록을 출력한다.

- [x] **[UI]** 도착 위치를 입력받는다. `class InputView`

- [x] **[Domain]** 검증을 통과하면 기물을 이동시키고 장기판을 갱신한다.

## 4. 승패 판정 및 게임 종료 (1차 PR 이후 구현)

- [x] **[Domain]** 상대의 '궁'이 잡혔는지 판단하여 게임 종료 여부를 결정한다.
    - [x] [규칙] 기물 이동 후, 한쪽의 '궁'이 보드판에서 사라졌다면 즉시 게임이 끝난다.

------------------------ ✂️ 2차 구현 및 PR 포인트 ------------------------

- [ ] **[UI]** 최종 승패 결과를 출력한다.
    - [ ] [출력] 상대방의 장을 잡은 진영(플레이어)을 승자로 출력한다.

## 3️⃣ 입출력 요구 사항

### 실행 결과 예시

```
선수(초) 플레이어의 이름을 입력하세요.
pobi

후수(한) 플레이어의 이름을 입력하세요.
jason

장기판의 현황은 다음과 같습니다. 

　　║　　0　　　　1　　　　2　　　　3　　　　4　　　　5　　　　6　　　　7　　　　8
　　║===============================================================
０　║［차０］━［마０］━［상０］━［사０］━［　　］━［사１］━［상１］━［마１］━［차１］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
１　║［　　］━［　　］━［　　］━［　　］━［한０］━［　　］━［　　］━［　　］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
２　║［　　］━［포０］━［　　］━［　　］━［　　］━［　　］━［　　］━［포１］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
３　║［병０］━［　　］━［병１］━［　　］━［병２］━［　　］━［병３］━［　　］━［병４］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
４　║［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
５　║［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
６　║［졸０］━［　　］━［졸１］━［　　］━［졸２］━［　　］━［졸３］━［　　］━［졸４］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
７　║［　　］━［포０］━［　　］━［　　］━［　　］━［　　］━［　　］━［포１］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
８　║［　　］━［　　］━［　　］━［　　］━［초０］━［　　］━［　　］━［　　］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
９　║［차０］━［마０］━［상０］━［사０］━［　　］━［사１］━［상１］━［마１］━［차１］

(초) 플레이어 pobi 님의 턴입니다. 

이동할 기물을 입력해주세요.
졸0

졸0 의 현재 좌표는 6 0 입니다.

이동할 좌표의 행을 입력해주세요.
6

이동할 좌표의 열을 입력해주세요.
1

장기판의 현황은 다음과 같습니다. 

　　║　　0　　　　1　　　　2　　　　3　　　　4　　　　5　　　　6　　　　7　　　　8
　　║===============================================================
０　║［차０］━［마０］━［상０］━［사０］━［　　］━［사１］━［상１］━［마１］━［차１］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
１　║［　　］━［　　］━［　　］━［　　］━［한０］━［　　］━［　　］━［　　］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
２　║［　　］━［포０］━［　　］━［　　］━［　　］━［　　］━［　　］━［포１］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
３　║［병０］━［　　］━［병１］━［　　］━［병２］━［　　］━［병３］━［　　］━［병４］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
４　║［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
５　║［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
６　║［　　］━［졸０］━［졸１］━［　　］━［졸２］━［　　］━［졸３］━［　　］━［졸４］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
７　║［　　］━［포０］━［　　］━［　　］━［　　］━［　　］━［　　］━［포１］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
８　║［　　］━［　　］━［　　］━［　　］━［초０］━［　　］━［　　］━［　　］━［　　］
　　║　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃　　　　┃
９　║［차０］━［마０］━［상０］━［사０］━［　　］━［사１］━［상１］━［마１］━［차１］

(한) 플레이어 jason 님의 턴입니다. 

```

---

## 4️⃣ 커밋 컨벤션

Following convention : https://gist.github.com/stephenparish/9941e89d80e2bc58a153

```markdown
# basic structure

<type>(<scope>): <subject>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>

# <type>

feat (feature)
fix (bug fix)
docs (documentation)
style (formatting, missing semi colons, …)
refactor
test (when adding missing tests)
chore (maintain)

# <scope>

console - I/O
domain - 핵심 로직
validation - 유효성검사
test - 테스트코드 추가
```

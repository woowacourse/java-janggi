# java-janggi

장기 미션 저장소

## 데이터베이스 세팅
1. 로컬 데이터베이스(mysql)에 chess 스키마를 생성한다.


2. 생성한 스키마 내부에서 다음 쿼리들을 순차적으로 실행해 테이블을 생성한다.
    ```
    create table game (
        id int NOT NULL,
        turn ENUM('GREEN', 'RED') NOT NULL,
        CONSTRAINT game_pk PRIMARY KEY(id)
    );
    
    create table board_piece (
        id int NOT NULL AUTO_INCREMENT,
        game_id int NOT NULL,
        column_value int NOT NULL,
        row_value int NOT NULL,
        piece_type ENUM('SOLDIER', 'GUARD', 'ELEPHANT', 'HORSE', 'CANON', 'CHARIOT', 'GENERAL') NOT NULL,
        team ENUM('GREEN', 'RED') NOT NULL,
        CONSTRAINT board_piece_pk PRIMARY KEY(id),
        CONSTRAINT game_board_piecefk foreign key (game_id) references game (id)
    );
    ```
3. IDEA에서 다음과 같이 Application 환경 변수를 설정한다.
    ```
   DB_USERNAME='개인 username';
   DB_PASSWORD='개인 비밀번호';
   DB_URL=jdbc:mysql://localhost:'포트번호'/chess?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    ```

## 기능 목록
- 게임 준비
  - [x] 이미 진행중인 게임이 존재하면 정보를 불러오고 이어서 진행한다.
  - [x] 진행중인 게임이 존재하지 않으면 새로운 게임을 준비한다.
    - [x] 장기판을 구성한다.
    - [x] 한나라 플레이어가 기물을 배치한다.
    - [x] 초나라 플레이어가 기물을 배치한다.
    - [x] 데이터베이스에 게임 정보를 저장한다.


- 게임 진행
  - [x] 초나라 플레이어가 먼저 기물을 이동한다.
  - [x] 이후 한나라 플레이어가 기물을 이동한다.
  - [x] 게임이 끝날 때까지 플레이어의 턴을 반복한다.
    - [x] 각 턴 시작 시 데이터베이스에서 턴 정보를 불러온다.
    - [x] 각 턴 종료 시 데이터베이스에 기물 정보를 업데이트한다.
    - [x] 각 턴 종료 시 데이터베이스에 턴 정보를 업데이트한다.
  - [x] 상대방의 장군을 잡으면 승리한다.

  
- 기물 이동
  - 졸/병
    - [x] 앞과 양 옆으로 한칸만 이동이 가능하다.
    - [x] 궁성 내에서는 선을 따라 앞과 양 옆으로 한칸 이동할 수 있다.
    - [x] 궁성 내에서는 선을 따라 대각으로 한 칸 앞으로 이동할 수 있다.
  - 궁과 사
    - [x] 상/하/좌/우로 한칸만 이동이 가능하다.
    - [x] 선을 따라 대각으로 한 칸만 이동이 가능하다.
    - [x] 각 진영의 궁성을 벗어날 시 예외를 발생한다.
  - 포
    - [x] 다른 포를 넘을 경우 예외를 발생한다.
    - [x] 다른 기물 하나를 넘지 않을 경우 예외를 발생한다.
    - [x] 포가 다른 포를 공격할 경우 예외를 발생한다.
    - [x] 궁성 내에서 위 조건을 만족하는 경우 상/하/좌/우/대각으로 이동할 수 있다.
  - 차
    - [x] 상/하/좌/우로 자유롭게 이동이 가능하다.
    - [x] 궁성 내에서 상/하/좌/우/대각으로 자유롭게 이동할 수 있다.
  - 상
    - [x] 상하좌우로 한칸 이동한 뒤 대각선으로 두칸 이동한다.
  - 마
    - [x] 상하좌우로 한칸 이동한 뒤 대각선으로 한칸 이동한다.
  - [x] 이동 경로에 기물이 존재하면 예외를 발생한다.
  - [x] 기물의 목적지에 상대의 기물이 있으면 잡을 수 있다.
  - [x] 출발 지점에 기물이 존재하지 않으면 예외를 발생한다.
  - [x] 목적지에 같은 진영의 기물이 존재하면 예외를 발생한다.
  - [x] 다른 진영의 기물을 움직일 경우, 예외를 발생한다.
  - [ ] 선이 존재하지 않는 대각 이동 시 예외를 발생한다.
 

- 게임 종료
  - [x] 남아있는 기물들의 점수를 합하여 플레이어의 점수를 계산한다.
    - 차(13), 포(7), 마(5), 상(3), 사(3), 졸/병(2)
  - [x] 게임 결과를 출력한다.
  - [x] 데이터베이스에 저장된 게임 정보를 삭제한다. 

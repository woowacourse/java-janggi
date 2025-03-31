# java-janggi

---

# 데이터베이스 연결 가이드

## MySQL 서버 주소, 유저 설정

- 필요에 따라 `src/main/java/janggi/dao/MysqlConnection.java`파일을 수정해서 사용할 수 있습니다.
- `SERVER`, `USERNAME`, `PASSWORD`는 필요에 따라 수정할 수 있습니다.
- `DATABASE`, `OPTION`은 수정할 수 없습니다.

## 어플리케이션 DB 생성하기

다음과 같이 DB를 생성한 후 어플리케이션을 실행할 수 있습니다.

```mysql
CREATE DATABASE janggi_test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi_test;

CREATE TABLE game
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    turn       ENUM ('CHO', 'HAN') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE piece
(
    game_id   INT,
    pieceType ENUM ('CANNON', 'CHARIOT', 'ELEPHANT', 'GENERAL', 'GUARD', 'HORSE', 'SOLDIER') NOT NULL,
    team      ENUM ('CHO', 'HAN')                                                            NOT NULL,
    col_num   INT                                                                            NOT NULL,
    row_num   INT                                                                            NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (id)
);
```

## 테스트용 DB 생성하기

다음과 같이 DB를 생성한 후 테스트를 실행할 수 있습니다.

```mysql
CREATE DATABASE janggi_test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi_test;

CREATE TABLE game
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    turn       ENUM ('CHO', 'HAN') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE piece
(
    game_id   INT,
    pieceType ENUM ('CANNON', 'CHARIOT', 'ELEPHANT', 'GENERAL', 'GUARD', 'HORSE', 'SOLDIER') NOT NULL,
    team      ENUM ('CHO', 'HAN')                                                            NOT NULL,
    col_num   INT                                                                            NOT NULL,
    row_num   INT                                                                            NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (id)
);
```

---

## 게임 룰

- [x] 상대의 궁을 잡으면 승리한다.
- [x] 초나라, 한나라 두개의 팀이 대결하며 초나라가 선공이다.
- [x] 상차림을 선택한다.

## 기물의 이동

### 궁 (General)

- [x] 궁은 궁성 내 그어진 선을 따라 한 칸 이동할 수 있다.
    - 콘솔 화면에서 궁성은 간략하게 표현되어 있다.
- [x] 궁은 궁성 밖으로 나갈 수 없다.

### 사 (Guard)

- [x] 사는 궁성 내 그어진 선을 따라 한 칸 이동할 수 있다.
    - 콘솔 화면에서 궁성은 간략하게 표현되어 있다.
- [x] 사는 궁성 밖으로는 나갈 수 없다.

### 졸/병 (Soldier)

- [x] 졸은 한나라의 기물로, 아래로 한 칸 또는 옆으로 한 칸 이동할 수 있다.
- [x] 병은 초나라의 기물로, 위로 한 칸 또는 옆으로 한 칸 이동할 수 있다.
- [x] 졸/병은 궁성에서 대각선을 따라 이동할 수 있다.
    - 단, 후퇴하는 방향으로는 이동할 수 없다.

### 마 (Horse)

- [x] 마는 직선으로 한 칸, 45도 대각선으로 한 칸 이동할 수 있다.
- [x] 이동 경로 첫 번째 칸에 다른 기물이 있는 경우 이동 할 수 없다.

### 상 (Elephant)

- [x] 상은 직선으로 한 칸, 45도 대각선으로 두 칸 이동할 수 있다.
- [x] 첫 번째 칸과 두 번째 칸에 다른 기물이 있는 경우 이동 할 수 없다.

### 차 (Chariot)

- [x] 차는 수평 또는 수직 직선으로 움직일 수 있다.
- [x] 차는 궁성에서 대각선을 따라 움직일 수 있다.
- [x] 차는 다른 말을 뛰어넘을 수 없다.

### 포 (Cannon)

- [x] 포는 다른 말을 뛰어 넘으면서 수평 또는 수직 직선으로 움직일 수 있다.
    - 포는 기물 하나만 뛰어 넘어야 한다.
    - 포는 포를 뛰어 넘을 수 없다.
- [x] 포는 포를 잡을 수 없다.
- [x] 포는 궁성에서 대각선으로 이동할 수 있다.

## 점수 계산

- [x] 보드에 남아있는 기물의 점수를 합산하여 계산할 수 있다.
    - 차: 13점
    - 포: 7점
    - 마: 5점
    - 상: 3점
    - 사: 3점
    - 병: 2점
- [x] 한은 후공이기 때문에 1.5점의 덤을 받는다.

## 게임의 저장

- [x] 게임을 실행하면 저장된 게임의 목록을 확인할 수 있다.
- [x] 게임 목록에서 0번을 선택하면 새로운 게임을 시작할 수 있다.
- [x] 게임 목록에서 저장된 게임을 선택하여 실행할 수 있다.
- [x] 게임을 중간에 종료하면 게임이 저장된다.
- [x] 게임에서 한나라 또는 초나라가 승리할 경우 게임 기록을 저장하지 않고 삭제한다.

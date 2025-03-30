DROP TABLE IF EXISTS board;
DROP TABLE IF EXISTS piece;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS team;

CREATE TABLE team
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE player
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    team_id BIGINT  NOT NULL,
    is_turn BOOLEAN NOT NULL,
    FOREIGN KEY (team_id) REFERENCES team (id)
);

CREATE TABLE piece
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    team_id BIGINT      NOT NULL,
    type    VARCHAR(50) NOT NULL, -- General, Cannon, Chariot 등
    FOREIGN KEY (team_id) REFERENCES team (id)
);

CREATE TABLE board
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    row_index    INT    NOT NULL,
    column_index INT    NOT NULL,
    piece_id     BIGINT NOT NULL,
    FOREIGN KEY (piece_id) REFERENCES piece (id)
);

-- 팀 생성
INSERT INTO team (name)
VALUES ('HAN'),
       ('CHO');

-- 플레이어 생성 (한 명만 턴 가짐)
INSERT INTO player (team_id, is_turn)
VALUES (1, true),
       (2, false);

-- HAN 말 배치
INSERT INTO piece (team_id, type)
VALUES (1, 'Soldier'),
       (1, 'Soldier'),
       (1, 'Soldier'),
       (1, 'Soldier'),
       (1, 'Soldier'),
       (1, 'Chariot'),
       (1, 'Chariot'),
       (1, 'Cannon'),
       (1, 'Cannon'),
       (1, 'Elephant'),
       (1, 'Elephant'),
       (1, 'Horse'),
       (1, 'Horse'),
       (1, 'General'),
       (1, 'Guard'),
       (1, 'Guard');

-- CHO 말 배치
INSERT INTO piece (team_id, type)
VALUES (2, 'Soldier'),
       (2, 'Soldier'),
       (2, 'Soldier'),
       (2, 'Soldier'),
       (2, 'Soldier'),
       (2, 'Chariot'),
       (2, 'Chariot'),
       (2, 'Cannon'),
       (2, 'Cannon'),
       (2, 'Elephant'),
       (2, 'Elephant'),
       (2, 'Horse'),
       (2, 'Horse'),
       (2, 'General'),
       (2, 'Guard'),
       (2, 'Guard');

-- HAN 보드 배치 (piece_id는 순서대로 부여됨)
-- Soldier: id 1~5
-- Chariot: id 6~7
-- Cannon: id 8~9
-- Elephant: id 10~11
-- Horse: id 12~13
-- General: id 14
-- Guard: id 15~16
INSERT INTO board (row_index, column_index, piece_id)
VALUES (6, 0, 1),
       (6, 2, 2),
       (6, 4, 3),
       (6, 6, 4),
       (6, 8, 5),
       (9, 0, 6),
       (9, 8, 7),
       (7, 1, 8),
       (7, 7, 9),
       (9, 1, 10),
       (9, 7, 11),
       (9, 2, 12),
       (9, 6, 13),
       (8, 4, 14),
       (9, 3, 15),
       (9, 5, 16);

-- CHO 보드 배치
-- Soldier: id 17~21
-- Chariot: id 22~23
-- Cannon: id 24~25
-- Elephant: id 26~27
-- Horse: id 28~29
-- General: id 30
-- Guard: id 31~32
INSERT INTO board (row_index, column_index, piece_id)
VALUES (3, 0, 17),
       (3, 2, 18),
       (3, 4, 19),
       (3, 6, 20),
       (3, 8, 21),
       (0, 0, 22),
       (0, 8, 23),
       (2, 1, 24),
       (2, 7, 25),
       (0, 1, 26),
       (0, 7, 27),
       (0, 2, 28),
       (0, 6, 29),
       (1, 4, 30),
       (0, 3, 31),
       (0, 5, 32);

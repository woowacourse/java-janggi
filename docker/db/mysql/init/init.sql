GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS `janggi` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS `janggi-test` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

CREATE TABLE IF NOT EXISTS board
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    team_code     VARCHAR(40) NOT NULL,
    current_camp  VARCHAR(3)  NOT NULL CHECK (current_camp IN ('CHO', 'HAN')),
    winner_camp   VARCHAR(4)  NOT NULL CHECK (winner_camp IN ('NONE', 'CHO', 'HAN')),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS piece
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    board_id BIGINT      NOT NULL,
    type     VARCHAR(8)  NOT NULL CHECK (type IN ('EMPTY', 'CANNON', 'CHARIOT', 'ELEPHANT', 'GENERAL', 'GUARD', 'HORSE', 'SOLDIER')),
    camp     VARCHAR(4)  NOT NULL CHECK (camp IN ('NONE', 'CHO', 'HAN')),
    x        INT         NOT NULL CHECK (x BETWEEN 0 AND 8),
    y        INT         NOT NULL CHECK (y BETWEEN 0 AND 9),
    PRIMARY KEY (id),
    FOREIGN KEY (board_id) REFERENCES board (id)
);

GRANT ALL PRIVILEGES ON `janggi`.* TO 'user'@'%';
FLUSH PRIVILEGES;


INSERT INTO board (team_code, current_camp, winner_camp)
VALUES ('RECENT_GAME', 'CHO', 'NONE');
SELECT LAST_INSERT_ID();

-- CHO 진영
INSERT INTO piece (board_id, type, camp, x, y) VALUES
(1, 'CHARIOT', 'CHO', 0, 0),
(1, 'HORSE', 'CHO', 1, 0),
(1, 'ELEPHANT', 'CHO', 2, 0),
(1, 'GUARD', 'CHO', 3, 0),
(1, 'GENERAL', 'CHO', 4, 0),
(1, 'GUARD', 'CHO', 5, 0),
(1, 'ELEPHANT', 'CHO', 6, 0),
(1, 'HORSE', 'CHO', 7, 0),
(1, 'CHARIOT', 'CHO', 8, 0),
(1, 'CANNON', 'CHO', 1, 2),
(1, 'CANNON', 'CHO', 7, 2),
(1, 'SOLDIER', 'CHO', 0, 3),
(1, 'SOLDIER', 'CHO', 2, 3),
(1, 'SOLDIER', 'CHO', 4, 3),
(1, 'SOLDIER', 'CHO', 6, 3),
(1, 'SOLDIER', 'CHO', 8, 3);

-- HAN 진영
INSERT INTO piece (board_id, type, camp, x, y) VALUES
(1, 'CHARIOT', 'HAN', 0, 9),
(1, 'HORSE', 'HAN', 1, 9),
(1, 'ELEPHANT', 'HAN', 2, 9),
(1, 'GUARD', 'HAN', 3, 9),
(1, 'GENERAL', 'HAN', 4, 9),
(1, 'GUARD', 'HAN', 5, 9),
(1, 'ELEPHANT', 'HAN', 6, 9),
(1, 'HORSE', 'HAN', 7, 9),
(1, 'CHARIOT', 'HAN', 8, 9),
(1, 'CANNON', 'HAN', 1, 7),
(1, 'CANNON', 'HAN', 7, 7),
(1, 'SOLDIER', 'HAN', 0, 6),
(1, 'SOLDIER', 'HAN', 2, 6),
(1, 'SOLDIER', 'HAN', 4, 6),
(1, 'SOLDIER', 'HAN', 6, 6),
(1, 'SOLDIER', 'HAN', 8, 6);

-- 모든 위치에 EMPTY 삽입 (기존 말 위치 제외)
INSERT INTO piece (board_id, type, camp, x, y)
SELECT 1, 'EMPTY', 'NONE', x, y
FROM (
    SELECT x, y
    FROM (
        SELECT x_series.n AS x, y_series.n AS y
        FROM (
            SELECT 0 AS n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4
            UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8
        ) AS x_series
        CROSS JOIN (
            SELECT 0 AS n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4
            UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9
        ) AS y_series
    ) AS all_positions
    WHERE (x, y) NOT IN (
        SELECT x, y FROM piece WHERE board_id = 1
    )
) AS empty_positions;

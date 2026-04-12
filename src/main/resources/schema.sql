DROP TABLE IF EXISTS pieces;
DROP TABLE IF EXISTS games;

CREATE TABLE games (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    turn        VARCHAR(10) NOT NULL,
    is_finished BOOLEAN     NOT NULL
);

CREATE TABLE pieces (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id    BIGINT      NOT NULL,
    x          INT         NOT NULL,
    y          INT         NOT NULL,
    piece_type VARCHAR(20) NOT NULL,
    team       VARCHAR(10) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);

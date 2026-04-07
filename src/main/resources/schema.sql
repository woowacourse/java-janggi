CREATE TABLE IF NOT EXISTS game
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    turn      VARCHAR(10) NOT NULL,
    is_active BOOLEAN     NOT NULL
);

CREATE TABLE IF NOT EXISTS piece
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT      NOT NULL,
    type    VARCHAR(20) NOT NULL,
    side    VARCHAR(10) NOT NULL,
    row_idx BIGINT      NOT NULL,
    col_idx BIGINT      NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (id)
);

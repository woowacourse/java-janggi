CREATE TABLE IF NOT EXISTS game
(
    game_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at        TIMESTAMP,
    cho_table_setting VARCHAR(20),
    han_table_setting VARCHAR(20),
    is_finished       BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS board
(
    board_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    x          INTEGER UNIQUE,
    y          INTEGER UNIQUE,
    piece_type VARCHAR(20),
    country    VARCHAR(10),
    game_id    BIGINT,
    FOREIGN KEY (game_id) REFERENCES game (game_id)
);

CREATE INDEX IF NOT EXISTS board_idx_game_id ON board (game_id);

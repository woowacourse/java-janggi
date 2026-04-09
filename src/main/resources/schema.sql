CREATE TABLE IF NOT EXISTS game_room (
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                   VARCHAR(255) NOT NULL,
    current_turn           VARCHAR(10) NOT NULL DEFAULT 'CHO',
    status                 VARCHAR(10) NOT NULL DEFAULT 'RUNNING',
    consecutive_pass_count INT NOT NULL DEFAULT 0,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS board_piece (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_room_id BIGINT NOT NULL,
    row_pos      INT NOT NULL,
    col_pos      INT NOT NULL,
    piece_type   VARCHAR(20) NOT NULL,
    team         VARCHAR(10) NOT NULL,
    FOREIGN KEY (game_room_id) REFERENCES game_room(id)
);
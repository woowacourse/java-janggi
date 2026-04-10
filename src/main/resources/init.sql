CREATE TABLE IF NOT EXISTS janggi_game (
    id BIGINT NOT NULL AUTO_INCREMENT,
    start_turn VARCHAR(8) NOT NULL,
    room_name VARCHAR(32) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS piece (
    id BIGINT NOT NULL AUTO_INCREMENT,
    janggi_game_id BIGINT NOT NULL,
    row_pos INT NOT NULL,
    col_pos INT NOT NULL,
    country VARCHAR(8) NOT NULL,
    type VARCHAR(16) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (janggi_game_id, row_pos, col_pos)
);

CREATE TABLE IF NOT EXISTS game_history (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    janggi_game_id BIGINT NOT NULL,
    board_snapshot TEXT NOT NULL
);

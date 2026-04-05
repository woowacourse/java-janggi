CREATE TABLE IF NOT EXISTS game_room(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_turn VARCHAR(10) NOT NULL,
    winner VARCHAR(10),
    cha_score DOUBLE,
    han_score DOUBLE
);
CREATE TABLE IF NOT EXISTS piece(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_room_id BIGINT NOT NULL,
    piece_name VARCHAR(10) NOT NULL,
    team VARCHAR(10) NOT NULL,
    row_pos INT NOT NULL,
    col_pos INT NOT NULL,
    FOREIGN KEY (game_room_id) REFERENCES game_room(id) ON DELETE CASCADE,
    UNIQUE (game_room_id, row_pos, col_pos)
);

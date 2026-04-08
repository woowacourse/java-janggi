CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    turn_side VARCHAR(10) NOT NULL,
    status VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS board_piece (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    row_index INT NOT NULL,
    column_index INT NOT NULL,
    piece_type VARCHAR(20) NOT NULL,
    piece_side VARCHAR(10) NOT NULL,
    CONSTRAINT fk_board_piece_game
        FOREIGN KEY (game_id) REFERENCES game(id)
        ON DELETE CASCADE,
    CONSTRAINT uq_board_piece_position
        UNIQUE (game_id, row_index, column_index)
);
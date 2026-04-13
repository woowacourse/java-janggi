CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_finished BOOLEAN DEFAULT FALSE,
    current_turn VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS piece (
    game_id BIGINT,
    row_index INT,
    col_index INT,
    piece_type VARCHAR(50),
    team VARCHAR(50),
    PRIMARY KEY (game_id, row_index, col_index),
    FOREIGN KEY (game_id) REFERENCES game(id)
);

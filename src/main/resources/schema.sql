CREATE TABLE IF NOT EXISTS game_meta (
    id INT PRIMARY KEY,
    current_turn VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS board_cell (
    cell_row INT NOT NULL,
    cell_col INT NOT NULL,
    team VARCHAR(20) NOT NULL,
    piece_type VARCHAR(30) NOT NULL,
    PRIMARY KEY (cell_row, cell_col)
);

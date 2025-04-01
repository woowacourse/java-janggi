CREATE TABLE if not exists coordinate(
    coordinate_id INT AUTO_INCREMENT PRIMARY KEY,
    piece_id INT NOT NULL,
    game_id INT NOT NULL,
    row_coordinate INT NOT NULL,
    col_coordinate INT NOT NULL,
    CONSTRAINT fk_coordinate_game_id FOREIGN KEY (game_id) REFERENCES game(game_id) on delete cascade,
    CONSTRAINT fk_coordinate_piece_id FOREIGN KEY (piece_id) REFERENCES piece(piece_id) on delete cascade
);
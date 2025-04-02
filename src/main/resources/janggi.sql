CREATE TABLE IF NOT EXISTS Piece (
   piece_id INT AUTO_INCREMENT NOT NULL,
   type VARCHAR(20) NOT NULL,
   side VARCHAR(10) NOT NULL,
   PRIMARY KEY (piece_id)
);

CREATE TABLE IF NOT EXISTS BoardPiece (
    board_piece_id INT AUTO_INCREMENT NOT NULL,
    piece_id INT NOT NULL,
    game_id INT NOT NULL,
    x INT NOT NULL,
    y INT NOT NULL,
    PRIMARY KEY (board_piece_id),
    UNIQUE(x, y),
    FOREIGN KEY (piece_id) REFERENCES Piece(piece_id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES Game(game_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Game (
    game_id INT AUTO_INCREMENT NOT NULL,
    state VARCHAR(10) NOT NULL,
    PRIMARY KEY (game_id)
);
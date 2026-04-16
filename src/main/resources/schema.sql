CREATE TABLE IF NOT EXISTS game
(
    game_id      INT PRIMARY KEY,
    current_turn VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS piece
(
    piece_id        INT AUTO_INCREMENT primary key,
    game_id         INT,
    team            VARCHAR(10) NOT NULL,
    piece_type      VARCHAR(10) NOT NULL,
    position_row    INT         NOT NULL,
    position_column INT         NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (game_id) ON DELETE CASCADE
);

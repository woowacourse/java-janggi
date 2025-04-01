CREATE TABLE if not exists piece(
    piece_id INT AUTO_INCREMENT PRIMARY KEY,
    country VARCHAR(5) NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    game_id INT,
    CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES game(game_id) ON DELETE CASCADE
);
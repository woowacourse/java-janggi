CREATE TABLE games (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_turn VARCHAR(10) NOT NULL,
    finished BOOLEAN NOT NULL,
    winner VARCHAR(10)
);

CREATE TABLE game_pieces (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    team VARCHAR(10) NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    x_value INT NOT NULL,
    y_value INT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(id)
);

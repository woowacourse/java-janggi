USE janggi;

CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_turn VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    chu_score DOUBLE DEFAULT 72,
    han_score DOUBLE DEFAULT 73.5
);

CREATE TABLE IF NOT EXISTS piece (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    piece_type VARCHAR(20) NOT NULL,
    team VARCHAR(10) NOT NULL,
    row_index INT NOT NULL,
    col_index INT NOT NULL,
    game_id BIGINT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game(id)
);



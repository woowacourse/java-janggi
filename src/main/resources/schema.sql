CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_turn VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS gimul (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    gimul_type VARCHAR(10) NOT NULL,
    team VARCHAR(10) NOT NULL,
    row_value INT NOT NULL,
    column_value INT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game(id)
    );

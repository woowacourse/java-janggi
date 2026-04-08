CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cho_player_name VARCHAR(5) NOT NULL,
    han_player_name VARCHAR(5) NOT NULL,
    cho_formation VARCHAR(30) NOT NULL,
    han_formation VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS move (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    turn_no INT NOT NULL,
    source_x INT NOT NULL,
    source_y INT NOT NULL,
    target_x INT NOT NULL,
    target_y INT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game(id),
    UNIQUE (game_id, turn_no)
);

CREATE INDEX IF NOT EXISTS idx_game_status ON game(status);
CREATE INDEX IF NOT EXISTS idx_move_game_turn ON move(game_id, turn_no);

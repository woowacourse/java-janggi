CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT,
    cho_player_name VARCHAR(255) NOT NULL,
    han_player_name VARCHAR(255) NOT NULL,
    cho_formation VARCHAR(255) NOT NULL,
    han_formation VARCHAR(255) NOT NULL,
    current_turn VARCHAR(255) NOT NULL,
    is_playing BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS move_history (
    id BIGINT NOT NULL AUTO_INCREMENT,
    game_id BIGINT NOT NULL,
    source_x INT NOT NULL,
    source_y INT NOT NULL,
    target_x INT NOT NULL,
    target_y INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES game (id) ON DELETE CASCADE
);

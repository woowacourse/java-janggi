CREATE TABLE IF NOT EXISTS game_record (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cho_formation VARCHAR(20) NOT NULL,
    han_formation VARCHAR(20) NOT NULL,
    game_status VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS move_record (
    id BIGINT NOT NULL AUTO_INCREMENT,
    game_record_id BIGINT NOT NULL,
    source_x INT NOT NULL,
    source_y INT NOT NULL,
    target_x INT NOT NULL,
    target_y INT NOT NULL,
    turn_side VARCHAR(10) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_move_record_game_record
        FOREIGN KEY (game_record_id) REFERENCES game_record (id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE DATABASE IF NOT EXISTS jangi_db;

USE jangi_db;

CREATE TABLE game_room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_turn VARCHAR(10) NOT NULL,
    is_finished BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_loaded_at DATETIME NULL,
    INDEX idx_created_at (created_at),
);

CREATE TABLE board_state (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT,
    row_pos INT,
    col_pos INT,
    piece_type VARCHAR(20),
    side VARCHAR(10),
    UNIQUE KEY (game_id, row_pos, col_pos),
    FOREIGN KEY (game_id)
        REFERENCES game_room(id)
        ON DELETE CASCADE
);

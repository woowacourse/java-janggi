DROP TABLE IF EXISTS game_state;
DROP TABLE IF EXISTS board_piece;
DROP TABLE IF EXISTS game_room;

CREATE TABLE IF NOT EXISTS game_room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS board_piece (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_room_id BIGINT,
    position_row INT,
    position_col INT,
    side VARCHAR(10),
    type VARCHAR(50),

    FOREIGN KEY (game_room_id) REFERENCES game_room(id)
);

CREATE TABLE IF NOT EXISTS game_state (
    game_room_id BIGINT PRIMARY KEY,
    current_turn VARCHAR(10),

    FOREIGN KEY (game_room_id) REFERENCES game_room(id) ON DELETE CASCADE
);
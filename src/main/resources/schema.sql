CREATE TABLE game_room (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE board_piece (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_room_id BIGINT,
    position_row INT,
    position_col INT,
    side VARCHAR(10),
    type VARCHAR(50),

    FOREIGN KEY (game_room_id) REFERENCES game_room(id)
);

CREATE TABLE game_state (
    game_room_id BIGINT PRIMARY KEY,
    current_turn VARCHAR(10),

    FOREIGN KEY (game_room_id) REFERENCES game_room(id) ON DELETE CASCADE
);
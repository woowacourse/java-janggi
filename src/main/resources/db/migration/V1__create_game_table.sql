CREATE TABLE if not exists game(
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(20) NOT NULL,
    curr_turn VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
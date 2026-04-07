CREATE TABLE IF NOT EXISTS game_rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PLAYING' -- PLAYING | FINISHED
);

CREATE TABLE IF NOT EXISTS games (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL REFERENCES game_rooms(id),
    current_state VARCHAR(20) NOT NULL DEFAULT 'READY_HAN', -- READY_HAN | READY_CHO | PLAYING | BIKJANG | END
    current_team VARCHAR(10) NOT NULL DEFAULT 'HAN',  -- HAN | CHO
    han_arrangement VARCHAR(20),   -- null이면 아직 미입력
    cho_arrangement VARCHAR(20),   -- null이면 아직 미입력
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS board_pieces (
    game_id BIGINT NOT NULL REFERENCES games(id),
    position VARCHAR(2)  NOT NULL,   -- "E1", "A0" 형식 (Column + Row)
    team VARCHAR(10) NOT NULL,
    piece_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (game_id, position)
);

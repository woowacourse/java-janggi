-- GAME_ROOMS
CREATE TABLE IF NOT EXISTS game_rooms (
    game_room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_turn VARCHAR(5) NOT NULL,
    game_status VARCHAR(10) NOT NULL,
    start_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    end_at TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- PIECES
CREATE TABLE IF NOT EXISTS pieces (
    piece_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_room_id BIGINT NOT NULL,
    FOREIGN KEY (game_room_id) REFERENCES game_rooms(game_room_id),
    camp VARCHAR(10) NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    row_position INT NOT NULL,
    col_position INT NOT NULL
);

DELETE
FROM pieces;
DELETE
FROM game_rooms;

INSERT INTO game_rooms(current_turn, game_status)
VALUES('CHO', 'PLAYING');

INSERT INTO game_rooms(current_turn, game_status)
VALUES('HAN', 'HAN_WIN');

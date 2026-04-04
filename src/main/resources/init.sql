-- DROP TABLE IF EXISTS piece_position;
-- DROP TABLE IF EXISTS game_room;

CREATE TABLE IF NOT EXISTS game_room
(
    janggi_game_id LONG AUTO_INCREMENT PRIMARY KEY,
    room_name      VARCHAR(10) NOT NULL,
    last_turn      VARCHAR(5)  NOT NULL CHECK (last_turn IN ('CHO', 'HAN')),
    last_played_at TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS piece_position
(
    piece_position INT AUTO_INCREMENT PRIMARY KEY,
    janggi_game_id LONG         NOT NULL,
    piece_row      INT         NOT NULL,
    piece_column   INT         NOT NULL,
    piece_type     VARCHAR(10) NOT NULL CHECK (piece_type IN
                                               ('CHARIOT', 'CANNON', 'HORSE', 'ELEPHANT', 'GUARD', 'SOLDIER',
                                                'GENERAL')),
    dynasty        VARCHAR(5)  NOT NULL CHECK (dynasty IN ('CHO', 'HAN')),

    CONSTRAINT fk_game
        FOREIGN KEY (janggi_game_id)
            REFERENCES game_room (janggi_game_id),

    CONSTRAINT uq_position
        UNIQUE (janggi_game_id, piece_row, piece_column)
);

CREATE TABLE IF NOT EXISTS game
(
    game_id LONG AUTO_INCREMENT PRIMARY KEY,
    room_name      VARCHAR(10) NOT NULL,
    current_turn      VARCHAR(5)  NOT NULL CHECK (current_turn IN ('CHO', 'HAN')),
    last_played_at TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS piece_position
(
    piece_position_id INT AUTO_INCREMENT PRIMARY KEY,
    game_id LONG         NOT NULL,
    piece_row      INT         NOT NULL,
    piece_column   INT         NOT NULL,
    piece_type     VARCHAR(10) NOT NULL CHECK (piece_type IN
                                               ('CHARIOT', 'CANNON', 'HORSE', 'ELEPHANT', 'GUARD', 'SOLDIER',
                                                'GENERAL')),
    dynasty        VARCHAR(5)  NOT NULL CHECK (dynasty IN ('CHO', 'HAN')),

    CONSTRAINT fk_game
        FOREIGN KEY (game_id)
            REFERENCES game (game_id),

    CONSTRAINT uq_position
        UNIQUE (game_id, piece_row, piece_column)
);

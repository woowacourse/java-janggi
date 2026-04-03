DROP TABLE IF EXISTS Piece_Position;
DROP TABLE IF EXISTS Game_Room;

CREATE TABLE Game_Room
(
    janggi_game_id INT AUTO_INCREMENT PRIMARY KEY,
    room_name      VARCHAR(10) NOT NULL,
    game_status    VARCHAR(10) NOT NULL CHECK (game_status IN ('PLAYING', 'FINISHED')),
    last_turn      VARCHAR(5)  NOT NULL CHECK (last_turn IN ('CHO', 'HAN')),
    last_played_at DATE        NOT NULL
);

CREATE TABLE Piece_Position
(
    piece_position INT AUTO_INCREMENT PRIMARY KEY,
    janggi_game_id INT         NOT NULL,
    piece_row            INT         NOT NULL,
    piece_column         INT         NOT NULL,
    piece_type     VARCHAR(10) NOT NULL CHECK (piece_type IN
                                               ('CHARIOT', 'CANNON', 'HORSE', 'ELEPHANT', 'GUARD', 'SOLDIER',
                                                'GENERAL')),
    dynasty        VARCHAR(5)  NOT NULL CHECK (dynasty IN ('CHO', 'HAN')),

    CONSTRAINT fk_game
        FOREIGN KEY (janggi_game_id)
            REFERENCES Game_Room (janggi_game_id),

    CONSTRAINT uq_position
        UNIQUE (janggi_game_id, piece_row, piece_column)
);



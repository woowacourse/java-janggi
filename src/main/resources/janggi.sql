-- Game
CREATE TABLE IF NOT EXISTS game
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    turn       VARCHAR(10)
        CHECK (turn IN ('CHO', 'HAN')),
    status     VARCHAR(20)  NOT NULL
        CHECK (status IN ('IN_PROGRESS', 'FINISHED')),
    winner     VARCHAR(10)
        CHECK (winner IN ('CHO', 'HAN') OR winner IS NULL),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Piece
CREATE TABLE IF NOT EXISTS piece
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    piece_type VARCHAR(20) NOT NULL
        CHECK (piece_type IN ('CHARIOT', 'CANNON', 'HORSE', 'ELEPHANT', 'SOLDIER', 'ADVISOR', 'GENERAL')),

    CONSTRAINT uq_piece_type UNIQUE (piece_type)
);

-- Board
CREATE TABLE IF NOT EXISTS board
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id  BIGINT      NOT NULL,
    piece_id BIGINT      NOT NULL,
    side     VARCHAR(10) NOT NULL
        CHECK (side IN ('CHO', 'HAN')),
    x        INT         NOT NULL,
    y        INT         NOT NULL,

    CONSTRAINT fk_board_game
        FOREIGN KEY (game_id)
            REFERENCES game (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_board_piece
        FOREIGN KEY (piece_id)
            REFERENCES piece (id),

    CONSTRAINT uq_board_point UNIQUE (game_id, x, y)
);

-- Move
CREATE TABLE IF NOT EXISTS move
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id    BIGINT      NOT NULL,

    piece_type VARCHAR(20) NOT NULL
        CHECK (piece_type IN ('CHARIOT', 'CANNON', 'HORSE', 'ELEPHANT', 'SOLDIER', 'ADVISOR', 'GENERAL')),

    side       VARCHAR(10) NOT NULL
        CHECK (side IN ('CHO', 'HAN')),

    from_x     INT         NOT NULL,
    from_y     INT         NOT NULL,
    to_x       INT         NOT NULL,
    to_y       INT         NOT NULL,

    timestamp  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_move_game
        FOREIGN KEY (game_id)
            REFERENCES game (id)
            ON DELETE CASCADE
);

MERGE INTO PIECE (ID, PIECE_TYPE)
    KEY (PIECE_TYPE)
    VALUES (1, 'CHARIOT');
MERGE INTO PIECE (ID, PIECE_TYPE)
    KEY (PIECE_TYPE)
    VALUES (2, 'CANNON');
MERGE INTO PIECE (ID, PIECE_TYPE)
    KEY (PIECE_TYPE)
    VALUES (3, 'HORSE');
MERGE INTO PIECE (ID, PIECE_TYPE)
    KEY (PIECE_TYPE)
    VALUES (4, 'ELEPHANT');
MERGE INTO PIECE (ID, PIECE_TYPE)
    KEY (PIECE_TYPE)
    VALUES (5, 'SOLDIER');
MERGE INTO PIECE (ID, PIECE_TYPE)
    KEY (PIECE_TYPE)
    VALUES (6, 'ADVISOR');
MERGE INTO PIECE (ID, PIECE_TYPE)
    KEY (PIECE_TYPE)
    VALUES (7, 'GENERAL');


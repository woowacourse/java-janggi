-- Game 테이블
CREATE TABLE IF NOT EXISTS game
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    cho_set_up VARCHAR(255) NOT NULL
        CHECK (cho_set_up IN ('LEFT_ELEPHANT', 'RIGHT_ELEPHANT', 'IN_ELEPHANT', 'OUT_ELEPHANT')),

    han_set_up VARCHAR(255) NOT NULL
        CHECK (han_set_up IN ('LEFT_ELEPHANT', 'RIGHT_ELEPHANT', 'IN_ELEPHANT', 'OUT_ELEPHANT')),

    status     VARCHAR(20)  NOT NULL
        CHECK (status IN ('IN_PROGRESS', 'FINISHED')),

    winner     VARCHAR(10)
        CHECK (winner IN ('CHO', 'HAN') OR winner IS NULL),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Board 테이블
CREATE TABLE IF NOT EXISTS board
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id    BIGINT      NOT NULL,
    piece_type VARCHAR(20) NOT NULL
        CHECK (piece_type IN ('CHARIOT', 'CANNON', 'HORSE', 'ELEPHANT', 'SOLDIER', 'ADVISOR', 'GENERAL')),
    x          INT         NOT NULL,
    y          INT         NOT NULL,
    side       VARCHAR(10) NOT NULL
        CHECK (side IN ('CHO', 'HAN')),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_board_game
        FOREIGN KEY (game_id)
            REFERENCES game (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_board_point UNIQUE (game_id, x, y)
);

-- Move 테이블
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

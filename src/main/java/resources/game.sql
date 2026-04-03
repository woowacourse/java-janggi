-- Game 테이블
CREATE TABLE game
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    cho_set_up VARCHAR(255) NOT NULL
        CHECK (status IN ('왼상차림', '오른상차림', '안상차림', '바깥상차림')),

    han_set_up VARCHAR(255) NOT NULL
        CHECK (status IN ('왼상차림', '오른상차림', '안상차림', '바깥상차림')),

    status     VARCHAR(20)  NOT NULL
        CHECK (status IN ('IN_PROGRESS', 'FINISHED')),

    winner     VARCHAR(10)
        CHECK (winner IN ('CHO', 'HAN') OR winner IS NULL),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Move 테이블
CREATE TABLE move
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    move_number INT AUTO_INCREMENT NOT NULL,
    game_id     BIGINT             NOT NULL,
    
    side        VARCHAR(10)        NOT NULL
        CHECK (side IN ('CHO', 'HAN')),

    from_x      INT                NOT NULL,
    from_y      INT                NOT NULL,
    to_x        INT                NOT NULL,
    to_y        INT                NOT NULL,

    timestamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_move_game
        FOREIGN KEY (game_id)
            REFERENCES game (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_game_move UNIQUE (game_id, move_number)
);

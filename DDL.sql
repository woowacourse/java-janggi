CREATE TABLE game
(
    id            BIGINT AUTO_INCREMENT        NOT NULL,
    status        ENUM ('RUNNING', 'FINISHED') NOT NULL,
    turn          INT                          NOT NULL,
    cho_score     INT                          NOT NULL,
    han_score     INT                          NOT NULL,
    started_at      TIMESTAMP                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_saved_at TIMESTAMP                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id)
);

CREATE TABLE piece
(
    id         BIGINT AUTO_INCREMENT                                                          NOT NULL,
    game_id    BIGINT                                                                         NOT NULL,
    row_pos    INT                                                                            NOT NULL,
    column_pos INT                                                                            NOT NULL,
    type       ENUM ('GENERAL', 'GUARD', 'SOLDIER', 'HORSE', 'ELEPHANT', 'CHARIOT', 'CANNON') NOT NULL,
    team       ENUM ('CHO', 'HAN')                                                            NOT NULL,

    PRIMARY KEY (id)
);

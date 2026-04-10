CREATE TABLE IF NOT EXISTS janggi_game
(
    id    BIGINT      NOT NULL AUTO_INCREMENT,
    turn  VARCHAR(8)  NOT NULL,
    state VARCHAR(16) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS piece
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    janggi_game_id BIGINT      NOT NULL,
    row_pos        INT         NOT NULL,
    col_pos        INT         NOT NULL,
    team           VARCHAR(8)  NOT NULL,
    type           VARCHAR(16) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_piece_game
        FOREIGN KEY (janggi_game_id) REFERENCES janggi_game (id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS movement
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    janggi_game_id BIGINT      NOT NULL,
    src_row_pos    INT         NOT NULL,
    src_col_pos    INT         NOT NULL,
    dest_row_pos   INT         NOT NULL,
    dest_col_pos   INT         NOT NULL,
    dest_team      VARCHAR(8)  NULL,
    dest_type      VARCHAR(16) NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_movement_game
        FOREIGN KEY (janggi_game_id) REFERENCES janggi_game (id)
            ON DELETE CASCADE
);

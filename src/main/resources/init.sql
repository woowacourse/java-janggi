CREATE TABLE IF NOT EXISTS janggi_game (
    id BIGINT NOT NULL AUTO_INCREMENT,
    turn VARCHAR(8) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS janggi_game_piece (
    id BIGINT NOT NULL AUTO_INCREMENT,
    janggi_game_id BIGINT NOT NULL,
    piece_id BIGINT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS janggi_game_movement (
    id BIGINT NOT NULL AUTO_INCREMENT,
    janggi_game_id BIGINT NOT NULL,
    movement_id BIGINT NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS piece (
    id BIGINT NOT NULL AUTO_INCREMENT,
    row_pos INT NOT NULL,
    col_pos INT NOT NULL,
    team VARCHAR(8) NOT NULL,
    type VARCHAR(16) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (row_pos, col_pos, team, type)
);

CREATE TABLE IF NOT EXISTS movement (
    id BIGINT NOT NULL AUTO_INCREMENT,
    source VARCHAR(8) NOT NULL,
    destination VARCHAR(8) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (source, destination)
);
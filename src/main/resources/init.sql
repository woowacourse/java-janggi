CREATE TABLE IF NOT EXISTS janggi_game (
    id BIGINT NOT NULL AUTO_INCREMENT,
    turn VARCHAR(8) NOT NULL,

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
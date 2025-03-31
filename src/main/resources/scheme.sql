CREATE TABLE games
(
    id           BIGINT      NOT NULL AUTO_INCREMENT,
    name         VARCHAR(64) NOT NULL UNIQUE,
    current_turn varchar(64) not null,
    PRIMARY KEY (id)
);

CREATE TABLE pieces
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    piece_name VARCHAR(64) NOT NULL,
    x          INT         NOT NULL,
    y          INT         NOT NULL,
    country    VARCHAR(64) NOT NULL,
    game_id    BIGINT      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES games (id)
);

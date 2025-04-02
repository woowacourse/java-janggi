USE janggi;

CREATE TABLE IF NOT EXISTS janggi_game
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    turn VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS piece
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    x              INTEGER NOT NULL,
    y              INTEGER NOT NULL,
    type           VARCHAR(20),
    team           VARCHAR(20),
    score          DOUBLE  NOT NULL,
    janggi_game_id BIGINT,
    FOREIGN KEY (janggi_game_id) REFERENCES janggi_game (id)
)

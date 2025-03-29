USE `wodnd0131`;

CREATE TABLE IF NOT EXISTS game
(
    id        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_active BOOLEAN Default true
);

CREATE TABLE IF NOT EXISTS player
(
    id      INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    team    VARCHAR(50) NOT NULL,
    score   DOUBLE      NOT NULL,
    is_turn BOOLEAN     NOT NULL,
    game_id INT,
    FOREIGN KEY (game_id) REFERENCES game (id)
);

CREATE TABLE IF NOT EXISTS piece
(
    id           INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    piece_type   VARCHAR(50) NOT NULL,
    piece_row    INT         NOT NULL,
    piece_column INT         NOT NULL,
    player_id    INT,
    FOREIGN KEY (player_id) REFERENCES player (id)
);

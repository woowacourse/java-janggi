USE `wodnd0131`;

CREATE TABLE IF NOT EXISTS game
(
    id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    is_activate BOOLEAN Default true
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

CREATE TABLE IF NOT EXISTS board_location
(
    id              INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    location_piece  VARCHAR(50) NOT NULL,
    location_row    INT         NOT NULL,
    location_column INT         NOT NULL,
    player_id       INT,
    FOREIGN KEY (player_id) REFERENCES player (id)
);

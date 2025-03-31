use Janggi;

CREATE TABLE player (
    id  INT NOT NULL AUTO_INCREMENT,
    score INT NOT NULL,
    team ENUM ('HAN', 'CHO') NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE piece (
    id INT NOT NULL AUTO_INCREMENT,
    player_id INT NOT NULL,
    type ENUM ('CANNON', 'CHARIOT', 'ELEPHANT', 'GENERAL', 'GUARD', 'HORSE', 'SOLDIER'),
    x INT NOT NULL,
    y INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (player_id) REFERENCES player (id) ON DELETE CASCADE
);

CREATE TABLE turn (
    id INT NOT NULL AUTO_INCREMENT,
    current_turn ENUM('HAN', 'CHO') NOT NULL,
    PRIMARY KEY(id)
)

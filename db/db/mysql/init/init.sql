use Janggi;

CREATE TABLE piece (
    id INT NOT NULL AUTO_INCREMENT,
    type ENUM ('CANNON', 'CHARIOT', 'ELEPHANT', 'GENERAL', 'GUARD', 'HORSE', 'SOLDIER'),
    team ENUM('HAN', 'CHO'),
    x INT NOT NULL,
    y INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE turn (
    id INT NOT NULL AUTO_INCREMENT,
    current_turn ENUM('HAN', 'CHO') NOT NULL,
    PRIMARY KEY(id)
)

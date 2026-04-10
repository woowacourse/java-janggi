DROP TABLE IF EXISTS piece;
DROP TABLE IF EXISTS game;

CREATE TABLE game (
    id INT NOT NULL AUTO_INCREMENT,
    current_turn ENUM('CHO', 'HAN') NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE piece (
    id INT NOT NULL AUTO_INCREMENT,
    game_id INT NOT NULL,
    `row` INT NOT NULL,
    `file` INT NOT NULL,
    side ENUM('CHO', 'HAN') NOT NULL,
    type ENUM('Cannon', 'Chariot', 'Elephant', 'General', 'Guard', 'Horse', 'Soldier') NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES game(id)
);

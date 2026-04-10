CREATE TABLE IF NOT EXISTS Game (
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    state VARCHAR(50),
    turn VARCHAR(50),
    start_date DATE DEFAULT (CURRENT_DATE),
    modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Piece (
    game_id INT,
    type VARCHAR(50),
    team VARCHAR(50),
    `row` INT,
    col INT,
    PRIMARY KEY (game_id, `row`, col),
    FOREIGN KEY (game_id) REFERENCES Game(game_id) ON DELETE CASCADE
);

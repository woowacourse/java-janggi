CREATE TABLE IF NOT EXISTS Game (
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    state VARCHAR(50),
    turn VARCHAR(50),
    start_date DATE
);

CREATE TABLE IF NOT EXISTS Piece (
    game_id INT,
    type VARCHAR(50),
    team VARCHAR(50),
    `row` INT,
    col INT
);

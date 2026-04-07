
CREATE TABLE IF NOT EXISTS game_state (
    id INT AUTO_INCREMENT PRIMARY KEY,
    turn VARCHAR(10)
    );

CREATE TABLE IF NOT EXISTS piece_state (
    id INT AUTO_INCREMENT PRIMARY KEY,game_id INT,
    country VARCHAR(10),
    piece_name VARCHAR(20),
    row_pos INT,
    col_pos INT,
    FOREIGN KEY (game_id) REFERENCES game_state(id) ON DELETE CASCADE
    );

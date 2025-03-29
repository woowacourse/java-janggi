USE `wodnd0131`;

CREATE TABLE IF NOT EXISTS room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    team VARCHAR(50) NOT NULL,
    is_turn BOOLEAN NOT NULL,
    room_id INT,
    FOREIGN KEY (room_id) REFERENCES room(id)
);

CREATE TABLE IF NOT EXISTS piece (
    id INT AUTO_INCREMENT PRIMARY KEY,
    piece_type VARCHAR(50) NOT NULL,
    piece_row INT,
    piece_column INT,
    player_id INT,
    FOREIGN KEY (player_id) REFERENCES player(id)
);

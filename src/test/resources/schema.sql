CREATE TABLE IF NOT EXISTS game (
   id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) UNIQUE,
    status VARCHAR(20),
    current_turn VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS piece (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id VARCHAR(36),
    piece_name VARCHAR(20),
    camp VARCHAR(10),
    row_index INT,
    column_index INT
);
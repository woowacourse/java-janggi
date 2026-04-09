-- GAMES
CREATE TABLE IF NOT EXISTS games (
    game_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    current_turn VARCHAR(5) NOT NULL CHECK (current_turn IN ('CHO', 'HAN')),
    game_status VARCHAR(10) NOT NULL CHECK (game_status IN ('PLAYING', 'CHO_WIN', 'HAN_WIN')),
    start_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    end_at TIMESTAMP,
    last_updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- PIECES
CREATE TABLE IF NOT EXISTS pieces (
    piece_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(game_id),
    camp VARCHAR(10) NOT NULL CHECK (camp IN ('CHO', 'HAN')),
    piece_type VARCHAR(10) NOT NULL CHECK (piece_type IN ('GENERAL', 'CHARIOT', 'HORSE', 'CANNON', 'GUARD', 'ELEPHANT', 'SOLDIER')),
    row_position INT NOT NULL,
    col_position INT NOT NULL
);

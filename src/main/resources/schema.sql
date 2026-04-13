CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    current_side VARCHAR(20) NOT NULL,
    move_count INT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS game_participant (
    game_id BIGINT NOT NULL,
    side VARCHAR(20) NOT NULL,
    player_name VARCHAR(5) NOT NULL,
    formation VARCHAR(30) NOT NULL,
    PRIMARY KEY (game_id, side),
    CONSTRAINT fk_game_participant_game FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS game_piece (
    game_id BIGINT NOT NULL,
    x INT NOT NULL,
    y INT NOT NULL,
    piece_side VARCHAR(20) NOT NULL,
    piece_type VARCHAR(30) NOT NULL,
    PRIMARY KEY (game_id, x, y),
    CONSTRAINT fk_game_piece_game FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE
);

ALTER TABLE game ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'IN_PROGRESS' NOT NULL;
ALTER TABLE game ADD COLUMN IF NOT EXISTS current_side VARCHAR(20) DEFAULT 'CHO' NOT NULL;
ALTER TABLE game ADD COLUMN IF NOT EXISTS move_count INT DEFAULT 0 NOT NULL;
ALTER TABLE game ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;

CREATE INDEX IF NOT EXISTS idx_game_status ON game(status);
CREATE INDEX IF NOT EXISTS idx_game_participant_game_id ON game_participant(game_id);
CREATE INDEX IF NOT EXISTS idx_game_piece_game_id ON game_piece(game_id);

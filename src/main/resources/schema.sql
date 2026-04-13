CREATE TABLE IF NOT EXISTS game_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    current_turn VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS piece_snapshot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    row_number INT NOT NULL,
    column_number INT NOT NULL,
    team_color VARCHAR(10) NOT NULL,
    piece_type VARCHAR(20) NOT NULL,
    CONSTRAINT fk_piece_snapshot_session
        FOREIGN KEY (session_id) REFERENCES game_session(id) ON DELETE CASCADE
);

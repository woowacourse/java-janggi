ALTER TABLE game ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE game ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE game ALTER COLUMN turn_side RENAME TO turn;

ALTER TABLE board_piece ALTER COLUMN row_index RENAME TO board_row;
ALTER TABLE board_piece ALTER COLUMN column_index RENAME TO board_column;

ALTER TABLE board_piece ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE board_piece ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE IF NOT EXISTS move_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    move_order INT NOT NULL,
    moving_piece_type VARCHAR(20) NOT NULL,
    moving_piece_side VARCHAR(10) NOT NULL,
    departure_row INT NOT NULL,
    departure_column INT NOT NULL,
    destination_row INT NOT NULL,
    destination_column INT NOT NULL,
    is_captured BOOLEAN NOT NULL,
    captured_piece_type VARCHAR(20),
    captured_piece_side VARCHAR(10),
    captured_row INT,
    captured_column INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_move_history_game
        FOREIGN KEY (game_id) REFERENCES game(id)
        ON DELETE CASCADE,
    CONSTRAINT uq_move_history_order
        UNIQUE (game_id, move_order)
);
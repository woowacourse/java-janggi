CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_cho VARCHAR(50) NOT NULL,
    player_han VARCHAR(50) NOT NULL,
    current_turn VARCHAR(10) NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS piece (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    piece_type VARCHAR(50) NOT NULL,
    side VARCHAR(50) NOT NULL,
    CONSTRAINT uq_piece_type_side UNIQUE (piece_type, side)
);

MERGE INTO piece (piece_type, side) KEY(piece_type, side) VALUES
    ('GENERAL', 'CHO'),
    ('GENERAL', 'HAN'),
    ('CHARIOT', 'CHO'),
    ('CHARIOT', 'HAN'),
    ('CANNON', 'CHO'),
    ('CANNON', 'HAN'),
    ('HORSE', 'CHO'),
    ('HORSE', 'HAN'),
    ('ELEPHANT', 'CHO'),
    ('ELEPHANT', 'HAN'),
    ('GUARD', 'CHO'),
    ('GUARD', 'HAN'),
    ('SOLDIER', 'CHO'),
    ('SOLDIER', 'HAN');

CREATE TABLE IF NOT EXISTS board (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    piece_id BIGINT NOT NULL,
    pos_row INT NOT NULL,
    pos_col INT NOT NULL,

    CONSTRAINT uq_piece_position UNIQUE (game_id, pos_row, pos_col),
    CONSTRAINT fk_board_game FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE,
    CONSTRAINT fk_board_piece FOREIGN KEY (piece_id) REFERENCES piece(id)
);

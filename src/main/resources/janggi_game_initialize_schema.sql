CREATE TABLE IF NOT EXISTS game (
    game_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(10) NOT NULL,
    current_player_id BIGINT
);

CREATE TABLE IF NOT EXISTS player (
    player_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    name VARCHAR(10) NOT NULL,
    team VARCHAR(10) NOT NULL,
    score INT NOT NULL,
    CONSTRAINT uq_player_game_team UNIQUE (game_id, team),
    CONSTRAINT fk_player_game FOREIGN KEY (game_id) REFERENCES game(game_id)
);

CREATE TABLE IF NOT EXISTS piece (
    piece_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    team VARCHAR(10) NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    board_row INT NOT NULL,
    board_column INT NOT NULL,
    CONSTRAINT uq_piece_game_position UNIQUE (game_id, board_row, board_column),
    CONSTRAINT fk_piece_game FOREIGN KEY (game_id) REFERENCES game(game_id)
    );

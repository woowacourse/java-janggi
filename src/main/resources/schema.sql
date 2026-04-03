CREATE TABLE IF NOT EXISTS games
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    name        varchar(255) NOT NULL,
    turns_taken numeric,
    team_queue  varchar(255) NOT NULL,
    CHECK (team_queue in ('RED,BLUE', 'BLUE,RED'))
);

CREATE TABLE IF NOT EXISTS board_cells
(
    id         bigint AUTO_INCREMENT PRIMARY KEY,
    row_pos    numeric,
    column_pos numeric,
    piece_type varchar(30) NOT NULL,
    team       varchar(30) NOT NULL,
    game_id    bigint,
    FOREIGN KEY (game_id) REFERENCES games (id) ON DELETE CASCADE,
    CHECK (row_pos >= 1 AND row_pos <= 10 AND column_pos >= 1 AND column_pos <= 9)
        AND (piece_type in
             ('GENERAL', 'SOLDIER', 'CANNON', 'CHARIOT', 'ELEPHANT', 'GUARD', 'HORSE'))
        AND (team in ('RED', 'BLUE'))
);

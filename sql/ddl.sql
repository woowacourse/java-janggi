CREATE TABLE IF NOT EXISTS game (
    game_id         INTEGER     PRIMARY KEY AUTOINCREMENT,
    current_turn    VARCHAR(10) NOT NULL,
    created_at      DATETIME    DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE IF NOT EXISTS piece (
    game_id         INTEGER     NOT NULL,
    position_row    INTEGER,
    position_file   INTEGER,
    side            VARCHAR(10) NOT NULL,
    piece_type      VARCHAR(10) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game(game_id),
    PRIMARY KEY (game_id, position_row, position_file)
);

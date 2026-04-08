CREATE TABLE IF NOT EXISTS janggi_game
(
    id     INTEGER PRIMARY KEY AUTOINCREMENT,
    status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS move_history
(
    game_id     INTEGER NOT NULL,
    turn_number INTEGER NOT NULL,
    start_x     INTEGER NOT NULL,
    start_y     INTEGER NOT NULL,
    end_x       INTEGER NOT NULL,
    end_y       INTEGER NOT NULL,
    PRIMARY KEY (game_id, turn_number),
    FOREIGN KEY (game_id) REFERENCES janggi_game (id)
);

CREATE TABLE IF NOT EXISTS games (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    turn TEXT NOT NULL,
    status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS pieces (
    game_id INTEGER NOT NULL,
    row_index INTEGER NOT NULL,
    column_index INTEGER NOT NULL,
    name TEXT NOT NULL,
    team TEXT NOT NULL,
    PRIMARY KEY (game_id, row_index, column_index),
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);

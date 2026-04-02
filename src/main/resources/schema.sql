CREATE TABLE IF NOT EXISTS game_states
(
    id          int AUTO_INCREMENT PRIMARY KEY,
    turns_taken int,
    team_queue  varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS boards
(
    id   int AUTO_INCREMENT PRIMARY KEY,
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS board_cells
(
    id         int AUTO_INCREMENT PRIMARY KEY,
    "row"      int,
    "column"   int,
    piece_type varchar(30) NOT NULL,
    team       varchar(30) NOT NULL,
    board_id   int,
    FOREIGN KEY (board_id) REFERENCES boards (id)
);

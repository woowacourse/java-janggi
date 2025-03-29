CREATE TABLE IF NOT EXISTS team
(
    name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS piece_type
(
    name VARCHAR(20) UNIQUE NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS game_room
(
    name VARCHAR(100) PRIMARY KEY,
    turn VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS piece
(
    id              BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    row_index       INT          NOT NULL,
    column_index    INT          NOT NULL,
    piece_type_name VARCHAR(20)  NOT NULL,
    team_name       VARCHAR(20)  NOT NULL,
    game_room_name  VARCHAR(100) NOT NULL,

    UNIQUE (row_index, column_index, game_room_name),

    FOREIGN KEY (piece_type_name) REFERENCES piece_type (name)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (team_name) REFERENCES team (name)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (game_room_name) REFERENCES game_room (name)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
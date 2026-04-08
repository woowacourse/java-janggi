CREATE TABLE  IF NOT EXISTS piece
(
    id      INT PRIMARY KEY ,
    piece_type    VARCHAR,
    country VARCHAR
);

CREATE TABLE  IF NOT EXISTS game
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    state  VARCHAR
);

CREATE TABLE  IF NOT EXISTS piece_position
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    game_id  INT,
    piece_id  INT,
    x  INT,
    y  INT,
    FOREIGN KEY (piece_id) REFERENCES piece(id),
    FOREIGN KEY (game_id) REFERENCES game(id)
);

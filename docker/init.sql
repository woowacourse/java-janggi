use chess;

create table game (
    id int NOT NULL,
    turn ENUM('GREEN', 'RED') NOT NULL,
    CONSTRAINT game_pk PRIMARY KEY(id)
);

create table board_piece (
    id int NOT NULL AUTO_INCREMENT,
    game_id int NOT NULL,
    column_value int NOT NULL,
    row_value int NOT NULL,
    piece_type ENUM('SOLDIER', 'GUARD', 'ELEPHANT', 'HORSE', 'CANON', 'CHARIOT', 'GENERAL') NOT NULL,
    team ENUM('GREEN', 'RED') NOT NULL,
    CONSTRAINT board_piece_pk PRIMARY KEY(id),
    CONSTRAINT game_board_piecefk foreign key (game_id) references game (id)
);

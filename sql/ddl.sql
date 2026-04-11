CREATE TABLE game
(
    game_id int primary key auto_increment,
    turn    char(3) check (turn in 'HAN', 'CHO')
);

CREATE TABLE piece
(
    piece_id   int primary key auto_increment,
    game_id    int not null ,
    piece_type varchar(20) not null,
    team       char(3) check in (team in 'HAN', 'CHO'),
    row_idx    int         not null,
    col_idx    int         not null,
    foreign key (game_id) references game(game_id)
);


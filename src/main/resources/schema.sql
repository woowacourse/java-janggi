create table if not exists games (
    id bigint primary key,
    cho_player_name varchar(255) not null,
    han_player_name varchar(255) not null,
    current_team varchar(20) not null
);

create table if not exists board_pieces (
    game_id bigint not null,
    row_num int not null,
    column_num int not null,
    piece_type varchar(20) not null,
    team varchar(20) not null,
    primary key (game_id, row_num, column_num),
    foreign key (game_id) references games(id)
);

create table if not exists caught_pieces (
    game_id bigint not null,
    sequence_num int not null,
    piece_type varchar(20) not null,
    team varchar(20) not null,
    primary key (game_id, sequence_num),
    foreign key (game_id) references games(id)
);

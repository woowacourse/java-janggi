create table if not exists game (
    id bigint primary key,
    game_status varchar(50) not null
);

create table if not exists piece_state (
    game_id bigint not null,
    row_number int not null,
    col_number int not null,
    piece_type varchar(50) not null,
    team varchar(50) not null,
    primary key (game_id, row_number, col_number)
);

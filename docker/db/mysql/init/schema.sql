USE chess;
create table if not exists player(
    id integer auto_increment not null,
    username varchar(50) not null,
    team varchar(10) not null,
    primary key (id)
);

create table if not exists game(
    id integer auto_increment not null,
    room_name varchar(50) not null,
    turn varchar(10) not null,
    primary key (id)
);

create table if not exists board(
    id integer auto_increment not null,
    row_index integer not null,
    column_index integer not null,
    piece_type varchar(10) not null,
    team varchar(10) not null,
    primary key (id)
);
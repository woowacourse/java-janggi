use janggi;
create table if not exists janggi.game
(
    id           int primary key auto_increment,
    setup_option int                  not null,
    finished     tinyint(1) default 0 not null
);

create table if not exists janggi.moveHistory
(
    id           int primary key auto_increment,
    game_id      int not null,
    start_row    int not null,
    start_column int not null,
    end_row      int not null,
    end_column   int not null,
    foreign key (game_id) references game (id)
);

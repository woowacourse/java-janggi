DROP DATABASE IF EXISTS janggi;

CREATE DATABASE janggi DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE janggi;

create table game
(
    game_id int auto_increment
        primary key,
    turn    varchar(255) not null,
    name    varchar(255) not null,
    constraint name
        unique (name)
);

create table piece
(
    y       int          not null,
    x       int          not null,
    type    varchar(255) not null,
    team    varchar(255) not null,
    game_id int          not null,
    primary key (y, x, game_id),
    constraint piece_game_game_id_fk
        foreign key (game_id) references janggi.game (game_id)
);




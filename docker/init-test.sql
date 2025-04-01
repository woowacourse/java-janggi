use janggiTest;
create table if not exists janggiTest.testGame
(
    id           int primary key auto_increment,
    setup_option int                  not null,
    finished     tinyint(1) default 0 not null
);

create table if not exists janggiTest.testMoveHistory
(
    id           int primary key auto_increment,
    game_id      int not null,
    start_row    int not null,
    start_column int not null,
    end_row      int not null,
    end_column   int not null,
    foreign key (game_id) references testGame (id)
);
INSERT INTO testGame (id, setup_option, finished)
VALUES (1, 2, 0),
       (2, 1, 0);
INSERT INTO testMoveHistory (id, game_id, start_row, start_column, end_row, end_column)
VALUES (1, 1, 9, 2, 7, 3),
       (2, 1, 3, 4, 4, 4),
       (3, 1, 7, 1, 7, 4),
       (4, 2, 6, 8, 6, 7),
       (5, 2, 3, 8, 3, 7),
       (6, 2, 9, 8, 0, 8);

create table if not exists board
(
    id           bigint auto_increment primary key,
    current_turn varchar(10) not null default 'CHO',
    winner varchar(10) not null default 'NONE',
    cho_score double null,
    han_score double null,
    is_finished  boolean     not null default false,
    created_at timestamp default current_timestamp
);

create table if not exists intersection
(
    id         bigint auto_increment primary key,
    board_id   bigint      not null,
    y          int         not null,
    x          int         not null,
    piece_type varchar(10) not null default 'NONE',
    intersection_type varchar(20) not null default 'NORMAL_INTERSECTION',
    team       varchar(10) not null default 'NONE',

    constraint fk_board FOREIGN KEY (board_id) references board (id) on delete cascade,

    unique key (board_id, y, x)
)

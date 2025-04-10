create table board_piece
(
    board_piece_id int primary key auto_increment,
    room_name varchar(100) not null,
    piece_type     ENUM('KING', 'GUARD', 'HORSE', 'ELEPHANT', 'CANNON', 'CHARIOT', 'SOLDIER') not null,
    team           ENUM('CHO', 'HAN') not null,
    column_position         int         not null,
    row_position            int         not null
);

create table turn
(
    turn ENUM('CHO', 'HAN') primary key
);

create table game_room
(
    room_name varchar(100) primary key,
    turn ENUM('CHO', 'HAN') not null
);

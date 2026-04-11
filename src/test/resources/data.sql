insert into game (game_name, current_turn)
values ('테스트 게임입니다.', 'CHO');
insert into game (game_name, current_turn)
values ('보드가 저장되지 않은 게임입니다.', 'HAN');

insert into board_piece (game_id, type, team, x_position, y_position)
values (1, 'KING', 'CHO', 4, 8);
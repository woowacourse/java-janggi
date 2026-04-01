DELETE
FROM board_piece;
DELETE
FROM game_state;
DELETE
FROM game_room;

INSERT INTO game_room (name)
VALUES ('기본 게임');

INSERT INTO game_state (game_room_id, current_turn)
VALUES (1, 'CHO');


INSERT INTO board_piece (game_room_id, position_row, position_col, side, type)
VALUES (1, 10, 1, 'CHO', '차'),
       (1, 10, 2, 'CHO', '마'),
       (1, 10, 3, 'CHO', '상'),
       (1, 10, 4, 'CHO', '사'),
       (1, 9, 5, 'CHO', '궁'),
       (1, 10, 6, 'CHO', '사'),
       (1, 10, 7, 'CHO', '상'),
       (1, 10, 8, 'CHO', '마'),
       (1, 10, 9, 'CHO', '차');

INSERT INTO board_piece (game_room_id, position_row, position_col, side, type)
VALUES (1, 8, 2, 'CHO', '포'),
       (1, 8, 8, 'CHO', '포');

INSERT INTO board_piece (game_room_id, position_row, position_col, side, type)
VALUES (1, 7, 1, 'CHO', '졸'),
       (1, 7, 3, 'CHO', '졸'),
       (1, 7, 5, 'CHO', '졸'),
       (1, 7, 7, 'CHO', '졸'),
       (1, 7, 9, 'CHO', '졸');

INSERT INTO board_piece (game_room_id, position_row, position_col, side, type)
VALUES (1, 1, 1, 'HAN', '차'),
       (1, 1, 2, 'HAN', '마'),
       (1, 1, 3, 'HAN', '상'),
       (1, 1, 4, 'HAN', '사'),
       (1, 2, 5, 'HAN', '궁'),
       (1, 1, 6, 'HAN', '사'),
       (1, 1, 7, 'HAN', '상'),
       (1, 1, 8, 'HAN', '마'),
       (1, 1, 9, 'HAN', '차');

INSERT INTO board_piece (game_room_id, position_row, position_col, side, type)
VALUES (1, 3, 2, 'HAN', '포'),
       (1, 3, 8, 'HAN', '포');

INSERT INTO board_piece (game_room_id, position_row, position_col, side, type)
VALUES (1, 4, 1, 'HAN', '졸'),
       (1, 4, 3, 'HAN', '졸'),
       (1, 4, 5, 'HAN', '졸'),
       (1, 4, 7, 'HAN', '졸'),
       (1, 4, 9, 'HAN', '졸');
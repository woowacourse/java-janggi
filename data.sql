INSERT INTO GAME_ROOM (id, turn, title, state) VALUES
(1, 'CHO', 'Hello. I''m newbie', 'PLAYING'),
(2, 'CHO', 'Please Gosu Only', 'PLAYING'),
(3, 'CHO', 'HanSooyo kk', 'PLAYING');

INSERT INTO BOARD (team, piece_type, game_room_id, position_row, position_column) VALUES
('CHO', 'CHA',  1, 5, 1), ('CHO', 'MA',   1, 1, 2), ('CHO', 'SANG', 1, 1, 3), ('CHO', 'SA', 1, 1, 4),
('CHO', 'SA',   1, 1, 6), ('CHO', 'SANG', 1, 1, 7), ('CHO', 'MA',   1, 1, 8), ('CHO', 'CHA',1, 1, 9),
('CHO', 'JANG', 1, 2, 5),
('CHO', 'PO',   1, 3, 2), ('CHO', 'PO',   1, 3, 8),
('CHO', 'JOL',  1, 4, 2), ('CHO', 'JOL',  1, 4, 3), ('CHO', 'JOL',  1, 4, 5), ('CHO', 'JOL', 1, 4, 7), ('CHO', 'JOL', 1, 4, 9),
('HAN', 'CHA',  1, 10, 1), ('HAN', 'MA',   1, 10, 2), ('HAN', 'SANG', 1, 10, 3), ('HAN', 'SA', 1, 10, 4),
('HAN', 'SA',   1, 10, 6), ('HAN', 'SANG', 1, 10, 7), ('HAN', 'MA',   1, 8, 7), ('HAN', 'CHA',1, 10, 9),
('HAN', 'JANG', 1, 9, 5),
('HAN', 'PO',   1, 8, 2), ('HAN', 'PO',   1, 8, 5),
('HAN', 'BYEONG', 1, 7, 1), ('HAN', 'BYEONG', 1, 7, 3), ('HAN', 'BYEONG', 1, 7, 5), ('HAN', 'BYEONG', 1, 7, 7), ('HAN', 'BYEONG', 1, 7, 9),

('CHO', 'CHA',  2, 1, 1), ('CHO', 'SANG', 2, 1, 2), ('CHO', 'MA',   2, 1, 3), ('CHO', 'SA', 2, 1, 4),
('CHO', 'SA',   2, 1, 6), ('CHO', 'SANG', 2, 1, 7), ('CHO', 'MA',   2, 1, 8), ('CHO', 'CHA',2, 1, 9),
('CHO', 'JANG', 2, 2, 5),
('CHO', 'PO',   2, 3, 2), ('CHO', 'PO',   2, 3, 8),
('CHO', 'JOL',  2, 4, 1), ('CHO', 'JOL',  2, 4, 3), ('CHO', 'JOL',  2, 5, 5), ('CHO', 'JOL', 2, 4, 7), ('CHO', 'JOL', 2, 4, 9),
('HAN', 'CHA',  2, 10, 1), ('HAN', 'MA',   2, 10, 2), ('HAN', 'SANG', 2, 10, 3), ('HAN', 'SA', 2, 10, 4),
('HAN', 'SA',   2, 10, 6), ('HAN', 'MA',   2, 10, 7), ('HAN', 'SANG', 2, 10, 8), ('HAN', 'CHA',2, 10, 9),
('HAN', 'JANG', 2, 9, 5),
('HAN', 'PO',   2, 8, 2), ('HAN', 'PO',   2, 8, 8),
('HAN', 'BYEONG', 2, 7, 1), ('HAN', 'BYEONG', 2, 7, 3), ('HAN', 'BYEONG', 2, 7, 5), ('HAN', 'BYEONG', 2, 7, 7), ('HAN', 'BYEONG', 2, 7, 8),

('CHO', 'CHA',  3, 1, 1), ('CHO', 'SANG', 3, 1, 2), ('CHO', 'MA',   3, 1, 3), ('CHO', 'SA', 3, 1, 4),
('CHO', 'SA',   3, 1, 6), ('CHO', 'MA',   3, 1, 7), ('CHO', 'SANG', 3, 1, 8), ('CHO', 'CHA',3, 1, 9),
('CHO', 'JANG', 3, 2, 5),
('CHO', 'PO',   3, 3, 2), ('CHO', 'PO',   3, 3, 8),
('CHO', 'JOL',  3, 4, 1), ('CHO', 'JOL',  3, 4, 3), ('CHO', 'JOL',  3, 4, 5), ('CHO', 'JOL', 3, 4, 7), ('CHO', 'JOL', 3, 4, 9),
('HAN', 'CHA',  3, 10, 1), ('HAN', 'SANG', 3, 10, 2), ('HAN', 'MA',   3, 10, 3), ('HAN', 'SA', 3, 10, 4),
('HAN', 'SA',   3, 10, 6), ('HAN', 'MA',   3, 10, 7), ('HAN', 'SANG', 3, 10, 8), ('HAN', 'CHA',3, 10, 9),
('HAN', 'JANG', 3, 9, 5),
('HAN', 'PO',   3, 8, 2), ('HAN', 'PO',   3, 8, 8),
('HAN', 'BYEONG', 3, 7, 1), ('HAN', 'BYEONG', 3, 7, 3), ('HAN', 'BYEONG', 3, 7, 5), ('HAN', 'BYEONG', 3, 7, 7), ('HAN', 'BYEONG', 3, 7, 9);
INSERT INTO games(id, name, turns_taken, team_queue)
VALUES (1, '게임 1', 15, 'RED,BLUE');

INSERT INTO board_cells(row_pos, column_pos, piece_type, team, game_id)
VALUES (2, 5, 'GENERAL', 'RED', 1),
       (2, 6, 'GUARD', 'RED', 1),
       (3, 4, 'GUARD', 'RED', 1),
       (4, 5, 'ELEPHANT', 'RED', 1),
       (5, 3, 'SOLDIER', 'RED', 1),
       (5, 6, 'SOLDIER', 'BLUE', 1),
       (6, 4, 'CANNON', 'BLUE', 1),
       (8, 6, 'GUARD', 'BLUE', 1),
       (9, 5, 'GENERAL', 'BLUE', 1);
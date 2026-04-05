INSERT INTO GAME
    (room_name, current_turn, last_played_at)
VALUES ('ROOM1', 'CHO', '2026-04-05 13:13');

INSERT INTO PIECE_POSITION
    (game_id, piece_row, piece_column, piece_type, dynasty)
VALUES
-- CHO (초)
(1, 3, 5, 'GENERAL', 'CHO'), -- 중앙에서 움직임
(1, 4, 1, 'SOLDIER', 'CHO'),
(1, 5, 3, 'SOLDIER', 'CHO'), -- 전진
(1, 5, 5, 'SOLDIER', 'CHO'), -- 전진
(1, 4, 7, 'SOLDIER', 'CHO'),
(1, 4, 9, 'SOLDIER', 'CHO'),

(1, 3, 4, 'CANNON', 'CHO'),  -- 중앙으로 이동
(1, 3, 6, 'CANNON', 'CHO'),

(1, 1, 1, 'CHARIOT', 'CHO'),
(1, 2, 3, 'HORSE', 'CHO'),   -- 이동
(1, 2, 7, 'HORSE', 'CHO'),

(1, 1, 4, 'GUARD', 'CHO'),
(1, 1, 6, 'GUARD', 'CHO'),

-- HAN (한)
(1, 8, 5, 'GENERAL', 'HAN'), -- 약간 전진
(1, 7, 1, 'SOLDIER', 'HAN'),
(1, 6, 3, 'SOLDIER', 'HAN'), -- 전진
(1, 6, 5, 'SOLDIER', 'HAN'),
(1, 7, 7, 'SOLDIER', 'HAN'),

(1, 8, 4, 'CANNON', 'HAN'),
(1, 8, 6, 'CANNON', 'HAN'),

(1, 10, 1, 'CHARIOT', 'HAN'),
(1, 9, 3, 'HORSE', 'HAN'),   -- 이동
(1, 9, 7, 'HORSE', 'HAN'),

(1, 10, 4, 'GUARD', 'HAN'),
(1, 10, 6, 'GUARD', 'HAN');

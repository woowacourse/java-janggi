CREATE DATABASE IF NOT EXISTS janggi;
USE janggi;

-- 초기 배치
CREATE TABLE IF NOT EXISTS formation_template (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    template_name VARCHAR(50) NOT NULL,
    team VARCHAR(10) NOT NULL,

    CONSTRAINT uq_template_name_team UNIQUE (template_name, team)
);

-- 게임 테이블 초기화
CREATE TABLE IF NOT EXISTS game (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    current_turn VARCHAR(10) NOT NULL,
    cho_formation_id BIGINT NOT NULL,
    han_formation_id BIGINT NOT NULL,

    CONSTRAINT fk_game_cho_formation
    FOREIGN KEY (cho_formation_id)
    REFERENCES formation_template(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT fk_game_han_formation
    FOREIGN KEY (han_formation_id)
    REFERENCES formation_template(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
);

-- 상차림 실제 기물 배치
CREATE TABLE IF NOT EXISTS formation_piece_layout (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    formation_template_id BIGINT NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    board_row INT NOT NULL,
    board_column INT NOT NULL,

    CONSTRAINT fk_layout_template
    FOREIGN KEY (formation_template_id)
    REFERENCES formation_template(id)
    ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT uq_layout_position
    UNIQUE (formation_template_id, board_row, board_column)
);

-- 조회용 테이블(read-model): 실제 보드 저장 상태
CREATE TABLE IF NOT EXISTS current_piece_position (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    piece_type VARCHAR(10) NOT NULL,
    piece_team VARCHAR(10) NOT NULL,
    board_row INT NOT NULL,
    board_column INT NOT NULL,

    CONSTRAINT fk_current_board_game
    FOREIGN KEY (game_id)
    REFERENCES game(id)
    ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT uq_board_position
    UNIQUE (game_id, board_row, board_column)
);


-- 이벤트 소싱 테이블(쓰기 전용): 각 이동 상태 저장
CREATE TABLE IF NOT EXISTS move_event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    version BIGINT NOT NULL,
    from_row INT NOT NULL,
    from_column INT NOT NULL,
    to_row INT NOT NULL,
    to_column INT NOT NULL,

    CONSTRAINT fk_move_event_game
    FOREIGN KEY (game_id)
    REFERENCES game(id)
    ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT uq_move_sequence
    UNIQUE (game_id, version)
);

-- 상차림 템플릿 데이터 삽입
INSERT INTO formation_template (template_name, team) VALUES ('INNER_ELEPHANT', 'CHO');
INSERT INTO formation_template (template_name, team) VALUES ('OUTER_ELEPHANT', 'CHO');
INSERT INTO formation_template (template_name, team) VALUES ('RIGHT_ELEPHANT', 'CHO');
INSERT INTO formation_template (template_name, team) VALUES ('LEFT_ELEPHANT', 'CHO');

INSERT INTO formation_template (template_name, team) VALUES ('INNER_ELEPHANT', 'HAN');
INSERT INTO formation_template (template_name, team) VALUES ('OUTER_ELEPHANT', 'HAN');
INSERT INTO formation_template (template_name, team) VALUES ('RIGHT_ELEPHANT', 'HAN');
INSERT INTO formation_template (template_name, team) VALUES ('LEFT_ELEPHANT', 'HAN');

-- 초(CHO) 마상상마(INNER_ELEPHANT)
INSERT INTO formation_piece_layout (formation_template_id, piece_type, board_row, board_column) VALUES
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'ROOK', 10, 1),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'HORSE', 10, 2),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'ELEPHANT', 10, 3),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'GUARD', 10, 4),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'KING', 10, 5),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'GUARD', 10, 6),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'ELEPHANT', 10, 7),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'HORSE', 10, 8),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'ROOK', 10, 9),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'CANNON', 8, 2),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'CANNON', 8, 8),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 1),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 3),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 5),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 7),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 9);

-- 초(CHO) 상마마상(OUTER_ELEPHANT)
INSERT INTO formation_piece_layout (formation_template_id, piece_type, board_row, board_column) VALUES
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'ROOK', 10, 1),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'ELEPHANT', 10, 2),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'HORSE', 10, 3),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'GUARD', 10, 4),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'KING', 10, 5),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'GUARD', 10, 6),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'HORSE', 10, 7),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'ELEPHANT', 10, 8),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'ROOK', 10, 9),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'CANNON', 8, 2),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'CANNON', 8, 8),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 1),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 3),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 5),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 7),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 9);

-- 초(CHO) 마상마상(RIGHT_ELEPHANT)
INSERT INTO formation_piece_layout (formation_template_id, piece_type, board_row, board_column) VALUES
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'ROOK', 10, 1),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'HORSE', 10, 2),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'ELEPHANT', 10, 3),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'GUARD', 10, 4),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'KING', 10, 5),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'GUARD', 10, 6),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'HORSE', 10, 7),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'ELEPHANT', 10, 8),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'ROOK', 10, 9),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'CANNON', 8, 2),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'CANNON', 8, 8),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 1),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 3),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 5),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 7),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 9);

-- 초(CHO) 상마상마(LEFT_ELEPHANT)
INSERT INTO formation_piece_layout (formation_template_id, piece_type, board_row, board_column) VALUES
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'ROOK', 10, 1),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'ELEPHANT', 10, 2),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'HORSE', 10, 3),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'GUARD', 10, 4),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'KING', 10, 5),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'GUARD', 10, 6),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'ELEPHANT', 10, 7),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'HORSE', 10, 8),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'ROOK', 10, 9),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'CANNON', 8, 2),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'CANNON', 8, 8),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 1),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 3),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 5),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 7),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'CHO'), 'PAWN', 7, 9);

-- 한(HAN) 마상상마(INNER_ELEPHANT)
INSERT INTO formation_piece_layout (formation_template_id, piece_type, board_row, board_column) VALUES
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'ROOK', 1, 1),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'HORSE', 1, 2),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'ELEPHANT', 1, 3),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'GUARD', 1, 4),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'KING', 1, 5),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'GUARD', 1, 6),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'ELEPHANT', 1, 7),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'HORSE', 1, 8),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'ROOK', 1, 9),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'CANNON', 3, 2),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'CANNON', 3, 8),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 1),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 3),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 5),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 7),
((SELECT id FROM formation_template WHERE template_name = 'INNER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 9);

-- 한(HAN) 상마마상(OUTER_ELEPHANT)
INSERT INTO formation_piece_layout (formation_template_id, piece_type, board_row, board_column) VALUES
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'ROOK', 1, 1),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'ELEPHANT', 1, 2),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'HORSE', 1, 3),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'GUARD', 1, 4),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'KING', 1, 5),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'GUARD', 1, 6),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'HORSE', 1, 7),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'ELEPHANT', 1, 8),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'ROOK', 1, 9),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'CANNON', 3, 2),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'CANNON', 3, 8),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 1),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 3),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 5),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 7),
((SELECT id FROM formation_template WHERE template_name = 'OUTER_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 9);

-- 한(HAN) 상마상마(RIGHT_ELEPHANT - 패턴은 마상마상이나 HAN 전략은 상마상마임)
INSERT INTO formation_piece_layout (formation_template_id, piece_type, board_row, board_column) VALUES
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'ROOK', 1, 1),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'ELEPHANT', 1, 2),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'HORSE', 1, 3),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'GUARD', 1, 4),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'KING', 1, 5),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'GUARD', 1, 6),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'ELEPHANT', 1, 7),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'HORSE', 1, 8),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'ROOK', 1, 9),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'CANNON', 3, 2),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'CANNON', 3, 8),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 1),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 3),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 5),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 7),
((SELECT id FROM formation_template WHERE template_name = 'RIGHT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 9);

-- 한(HAN) 마상마상(LEFT_ELEPHANT - 패턴은 상마상마이나 HAN 전략은 마상마상임)
INSERT INTO formation_piece_layout (formation_template_id, piece_type, board_row, board_column) VALUES
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'ROOK', 1, 1),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'HORSE', 1, 2),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'ELEPHANT', 1, 3),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'GUARD', 1, 4),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'KING', 1, 5),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'GUARD', 1, 6),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'HORSE', 1, 7),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'ELEPHANT', 1, 8),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'ROOK', 1, 9),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'CANNON', 3, 2),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'CANNON', 3, 8),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 1),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 3),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 5),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 7),
((SELECT id FROM formation_template WHERE template_name = 'LEFT_ELEPHANT' AND team = 'HAN'), 'PAWN', 4, 9);

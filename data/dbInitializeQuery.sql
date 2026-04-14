CREATE DOMAIN SIDE_TYPE AS VARCHAR(10)
    CHECK (VALUE IN ('HAN', 'CHU', 'NEUTRAL'));

CREATE DOMAIN PIECE_TYPE AS VARCHAR(10)
    CHECK (VALUE IN ('KING', 'GUARD', 'ELEPHANT', 'HORSE', 'CHARIOT', 'CANNON', 'PAWN', 'EMPTY'));

CREATE TABLE game (
                      id   INTEGER PRIMARY KEY AUTO_INCREMENT,
                      turn SIDE_TYPE NOT NULL
);CREATE TABLE piece (
                         col_num    INTEGER    NOT NULL,
                         row_num    INTEGER    NOT NULL,
                         piece_type PIECE_TYPE NOT NULL,
                         side       SIDE_TYPE  NOT NULL,
                         PRIMARY KEY (col_num, row_num)
  );
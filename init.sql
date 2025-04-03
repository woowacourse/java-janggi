# 장기 데이터베이스 추가
DROP DATABASE IF EXISTS janggi;
CREATE DATABASE janggi;

USE janggi;

# 기물 테이블 추가
DROP TABLE IF EXISTS piece;
CREATE TABLE piece
(
    game_number   INT         NOT NULL,
    piece_type    VARCHAR(10) NOT NULL,
    position_file INT         NOT NULL,
    position_rank INT         NOT NULL,
    country       VARCHAR(10) NOT NULL,
    PRIMARY KEY (piece_type, position_rank, position_file)
);

# 턴 테이블 추가
DROP TABLE IF EXISTS turn;
CREATE TABLE turn
(
    game_number INT         NOT NULL,
    country     VARCHAR(10) NOT NULL,
    PRIMARY KEY (game_number)
);

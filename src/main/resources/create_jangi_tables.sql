/**
  jangi 데이터베이스 한글 설정
 */

ALTER DATABASE jangi DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE jangi;

/**
  game 테이블 생성, 조회
 */

CREATE TABLE game (
                id INT AUTO_INCREMENT PRIMARY KEY,
                turn VARCHAR(50) NOT NULL,
                created_at VARCHAR(50) NOT NULL
);

SELECT * FROM game;

/**
  piece 테이블 생성, 조회
 */

CREATE TABLE piece (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                is_running BOOLEAN NOT NULL DEFAULT TRUE,
                row_index INT NOT NULL,
                column_index INT NOT NULL,
                team VARCHAR(50) NOT NULL,
                game_id INT NOT NULL,
                FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE
);

SELECT * FROM piece;
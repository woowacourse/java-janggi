ALTER DATABASE janggi DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE janggi;

CREATE TABLE game (
                id INT AUTO_INCREMENT PRIMARY KEY,
                turn VARCHAR(50) NOT NULL,
                created_at VARCHAR(50) NOT NULL
);

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

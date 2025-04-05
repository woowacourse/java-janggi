use janggi;

CREATE TABLE IF NOT EXISTS pieces (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        type VARCHAR(20),
                        country VARCHAR(10),
                        x INT,
                        y INT
);

CREATE TABLE IF NOT EXISTS board_score (
                             country VARCHAR(10) PRIMARY KEY,
                             score INT
);

CREATE TABLE IF NOT EXISTS country_direction (
                                   country VARCHAR(10) PRIMARY KEY,
                                   direction VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS game_status (
                                id INT PRIMARY KEY CHECK (id = 1),
                                current_turn VARCHAR(10) NOT NULL,
                                turn_count INT NOT NULL
);

use janggi;

CREATE TABLE pieces (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        type VARCHAR(20),         -- 예: "Cannon", "Chariot"
                        country VARCHAR(10),      -- 예: "RED", "BLUE"
                        x INT,                    -- Position 내부 값
                        y INT
);

CREATE TABLE board_score (
                             country VARCHAR(10) PRIMARY KEY,
                             score INT
);

CREATE TABLE country_direction (
                                   country VARCHAR(10) PRIMARY KEY,
                                   direction VARCHAR(10) NOT NULL
);

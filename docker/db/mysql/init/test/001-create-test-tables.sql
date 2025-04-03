USE test;

CREATE TABLE piece (
                       `row` INT NOT NULL,
                       `column` INT NOT NULL,
                       team VARCHAR(50) NOT NULL,
                       piece_type VARCHAR(50) NOT NULL
);

CREATE TABLE player (
                        name VARCHAR(50) NOT NULL,
                        team VARCHAR(50) NOT NULL
);

CREATE TABLE board (
                       current_turn VARCHAR(50) NOT NULL
);

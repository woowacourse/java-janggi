CREATE TABLE board
(
    piece_name VARCHAR(64) NOT NULL,
    x          INT         NOT NULL,
    y          INT         NOT NULL,
    country    VARCHAR(64) NOT NULL,
    PRIMARY KEY (x, y)
);

CREATE TABLE turn
(
    current_turn varchar(64) not null
        primary key
);

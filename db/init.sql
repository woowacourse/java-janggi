USE janggi;

CREATE TABLE piece (
    position_row INT NOT NULL,
    position_col INT NOT NULL,
    piece_type VARCHAR(30) NOT NULL,
    team VARCHAR(30) NOT NULL,
    primary key (position_row, position_col)
);

CREATE TABLE turn (
    team VARCHAR(30) NOT NULL
);

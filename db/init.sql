USE janggi;

CREATE TABLE team (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE piece_type (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE piece (
    position_row INT NOT NULL,
    position_col INT NOT NULL,
    type_id INT NOT NULL,
    team_id INT NOT NULL,
    primary key (position_row, position_col),
    foreign key (type_id) references piece_type(id),
    foreign key (team_id) references team(id)
);

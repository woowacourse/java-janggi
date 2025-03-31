
CREATE TABLE team
(
    team_id int         NOT NULL AUTO_INCREMENT,
    name    VARCHAR(64) NOT NULL,
    PRIMARY KEY (team_id)
);


INSERT INTO team (name)
VALUES ('HAN'),
       ('CHO');

CREATE TABLE piecetype
(
    piecetype_id int         NOT NULL AUTO_INCREMENT,
    name         VARCHAR(64) NOT NULL,
    PRIMARY KEY (piecetype_id)
);


INSERT INTO piecetype (name)
VALUES ('CANNON'),
       ('CHARIOT'),
       ('CHO_SOLDIER'),
       ('HAN_SOLDIER'),
       ('ELEPHANT'),
       ('GUARD'),
       ('HORSE'),
       ('KING');


CREATE TABLE piece
(
    piece_id  int NOT NULL AUTO_INCREMENT,
    y         int NOT NULL,
    x         int NOT NULL,
    team      int references team (team_id),
    piecetype int references piecetype (piecetype_id),
    PRIMARY KEY (piece_id)
);


CREATE TABLE turn
(
    turn_id       int     NOT NULL AUTO_INCREMENT,
    team          int references team (team_id),
    isCurrentTeam boolean NOT NULL,
    PRIMARY KEY (turn_id)
);

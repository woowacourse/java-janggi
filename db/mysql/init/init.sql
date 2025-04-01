CREATE TABLE Piece (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       piece_type VARCHAR(50) NOT NULL,
                       team VARCHAR(10) NOT NULL,
                       x INT NOT NULL,
                       y INT NOT NULL
);

CREATE TABLE Turn (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      team VARCHAR(10) NOT NULL CHECK(team IN ('RED', 'BLUE'))
);

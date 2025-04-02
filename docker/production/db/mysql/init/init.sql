DROP TABLE IF EXISTS movePieceRecords;
DROP TABLE IF EXISTS games;

CREATE TABLE IF NOT EXISTS games (
       gameId INT PRIMARY KEY AUTO_INCREMENT,
       gameTitle VARCHAR(50),
       choAssignType VARCHAR(50),
       hanAssignType VARCHAR(50),
       gameState VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS movePieceRecords (
      recordId INT PRIMARY KEY AUTO_INCREMENT,
      gameId Int,
      campType VARCHAR(10),
      targetPieceXPosition INT,
      targetPieceYPosition INT,
      destinationXPosition INT,
      destinationYPosition INT,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (gameId) REFERENCES games(gameId)
);

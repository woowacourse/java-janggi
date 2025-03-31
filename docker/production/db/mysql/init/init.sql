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
      targetPieceXPostion INT,
      targetPieceYPostion INT,
      destinationXPostion INT,
      destinationYPostion INT,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (gameId) REFERENCES games(gameId)
);

DROP TABLE IF EXISTS move_piece_commands;
DROP TABLE IF EXISTS games;

CREATE TABLE IF NOT EXISTS games (
       game_id INT PRIMARY KEY AUTO_INCREMENT,
       title VARCHAR(50),
       cho_assign_type VARCHAR(50),
       han_assign_type VARCHAR(50),
       game_state VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS move_piece_commands (
      command_id INT PRIMARY KEY AUTO_INCREMENT,
      game_id Int,
      camp_type VARCHAR(10),
      target_piece_x_position INT,
      target_piece_y_position INT,
      destination_x_position INT,
      destination_y_position INT,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (game_id) REFERENCES games(game_id),
      INDEX index_by_game_id (game_id)
);

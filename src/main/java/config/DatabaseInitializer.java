package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String CREATE_GAME_TABLE = """
            CREATE TABLE IF NOT EXISTS game(
            id INT AUTO_INCREMENT PRIMARY KEY,
            turn VARCHAR(20) NOT NULL,
            finished BOOLEAN NOT NULL,
            winner VARCHAR(20)
            )
            """;

    private static final String CREATE_PIECE_TABLE = """
            CREATE TABLE IF NOT EXISTS piece(
                id INT AUTO_INCREMENT PRIMARY KEY,
                game_id INT NOT NULL,
                row_number INT NOT NULL,
                column_number INT NOT NULL,
                country VARCHAR(20) NOT NULL,
                piece_type VARCHAR(20) NOT NULL,
                CONSTRAINT fk_piece_game
                    FOREIGN KEY (game_id) REFERENCES game(id)
                    ON DELETE CASCADE
            )
            """;

    private DatabaseInitializer(){}

    public static void initialize() throws SQLException{
        try(Connection connection = DatabaseConfig.getConnection();
            Statement statement = connection.createStatement()){
            statement.execute(CREATE_GAME_TABLE);
            statement.execute(CREATE_PIECE_TABLE);
        }
    }
}

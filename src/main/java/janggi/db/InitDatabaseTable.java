package janggi.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabaseTable {
    private static final String CREATE_GAME_TABLE_SQL = "CREATE TABLE IF NOT EXISTS game (id INT PRIMARY KEY, current_turn VARCHAR(10));";
    private static final String CREATE_GAME_PIECE_SQL = "CREATE TABLE IF NOT EXISTS piece (id INT AUTO_INCREMENT PRIMARY KEY, game_id INT, position_row INT, position_column INT, piece_type VARCHAR(50), team_type VARCHAR(50), FOREIGN KEY (game_id) REFERENCES game(id));";

    public static void initDatabaseTable(DBConnector connector) {
        try (Connection connection = connector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(CREATE_GAME_TABLE_SQL);
            statement.execute(CREATE_GAME_PIECE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류");
        }
    }
}

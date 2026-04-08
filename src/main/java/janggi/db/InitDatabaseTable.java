package janggi.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabaseTable {
    private static final String sql1 = "CREATE TABLE IF NOT EXISTS game (id INT PRIMARY KEY, current_turn VARCHAR(10));";
    private static final String sql2 = "CREATE TABLE IF NOT EXISTS piece (id INT AUTO_INCREMENT PRIMARY KEY, game_id INT, position_row INT, position_column INT, piece_type VARCHAR(50), team_type VARCHAR(50), FOREIGN KEY (game_id) REFERENCES game(id));";

    public static void initDatabaseTable() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql1);
            statement.execute(sql2);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류");
        }
    }
}

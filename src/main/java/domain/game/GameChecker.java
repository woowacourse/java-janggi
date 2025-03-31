package domain.game;

import database.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameChecker {

    private final DbConnection dbConnection;

    public GameChecker() {
        this.dbConnection = DbConnection.getInstance();
    }

    public boolean checkIfGameExists(int gameId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM games WHERE game_id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }
}

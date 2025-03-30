package domain.game;

import database.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDao {

    private final DbConnection dbConnection;

    public GameDao() {
        dbConnection = DbConnection.getInstance();
    }

    public Games insertGame(int gameId, String startGame) {
        final var insertGameSql = "INSERT INTO games (game_id, game_status) VALUES (?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertGameSql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, startGame);
            preparedStatement.executeUpdate();

            return new Games(gameId, startGame, 0, -1, -1);

        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 저장 오류", e);
        }
    }
}

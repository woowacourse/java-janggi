package domain.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {

    private final Connection connection;

    public GameDao(Connection connection) {
        this.connection = connection;
    }

    public Games insertGame(int gameId) {
        final var insertGameSql = "INSERT INTO games (game_id,game_status) VALUES (?,?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertGameSql)) {

            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, Status.CREATED.name());
            preparedStatement.executeUpdate();

            return new Games(gameId, null, 0, -1, -1);

        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 저장 오류", e);
        }
    }

    public void updateGame(String status, int gameId, int bluePlayerId, int redPlayerId, int thisTurnSequence) {
        final var updateGameSql = "UPDATE games SET game_status = ?, player1_id = ?, player2_id = ?, current_turn = ? WHERE game_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(updateGameSql)) {

            // Assuming Players class has methods to get player IDs
            preparedStatement.setString(1,
                    status);  // Assuming "in progress" is a placeholder for the actual game status
            preparedStatement.setInt(2, bluePlayerId);  // Get Player 1's ID from the Players object
            preparedStatement.setInt(3, redPlayerId);  // Get Player 2's ID from the Players object
            preparedStatement.setInt(4, thisTurnSequence);  // Set the current turn sequence
            preparedStatement.setInt(5, gameId);  // Set the game ID to identify the game to update

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No game found with ID " + gameId);
            }

        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 업데이트 오류", e);
        }
    }

    public void updateGameSequence(int gameId, int thisTurnSequence) {
        final var updateGameSql = "UPDATE games SET current_turn = ? WHERE game_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(updateGameSql)) {

            preparedStatement.setInt(1, thisTurnSequence);
            preparedStatement.setInt(2, gameId);

            int rowsUpdated = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 업데이트 오류", e);
        }
    }

    public Games selectGameById(int gameId) {
        final var query = "SELECT * FROM games WHERE game_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Games(
                        resultSet.getInt("game_id"),
                        resultSet.getString("game_status"),
                        resultSet.getInt("current_turn"),
                        resultSet.getInt("player1_id"),
                        resultSet.getInt("player2_id")
                );
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("게임 불러오기 오류", e);
        }
        return null;
    }
}

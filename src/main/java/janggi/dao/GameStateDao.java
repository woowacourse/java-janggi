package janggi.dao;

import janggi.domain.piece.TeamColor;
import janggi.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class GameStateDao {

    public void saveStartGameState(TeamColor turnColor) {
        String query = "INSERT INTO GameState (turn_color, start_time) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            System.out.println(startTime);
            preparedStatement.setString(1, turnColor.name());
            preparedStatement.setTimestamp(2, startTime);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save game state", e);
        }
    }

    public void updateGameState(int gameId, TeamColor turnColor) {
        String query = "UPDATE GameState SET turn_color = ?, last_updated = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, turnColor.name());
            preparedStatement.setInt(2, gameId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update game state", e);
        }
    }

    public void finishGame(int gameId, TeamColor winner) {
        String query = "UPDATE GameState SET is_finished = TRUE, winner = ?, end_time = ?, last_updated = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            Timestamp endTime = new Timestamp(System.currentTimeMillis());

            preparedStatement.setString(1, winner.name());
            preparedStatement.setTimestamp(2, endTime);
            preparedStatement.setInt(3, gameId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to finish game", e);
        }
    }

    public Optional<Integer> getInProgressGameId() {
        String query = "SELECT id FROM GameState WHERE is_finished = FALSE ORDER BY start_time DESC LIMIT 1";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get ongoing game ID", e);
        }
    }
}

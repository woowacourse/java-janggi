package janggi.dao;

import janggi.domain.piece.TeamColor;
import janggi.dto.GameStateDto;
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
            preparedStatement.setString(1, turnColor.name());
            preparedStatement.setTimestamp(2, startTime);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Game State 저장 실패", e);
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
            throw new RuntimeException("Game State update 실패", e);
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
            throw new RuntimeException("Game 종료 update 실패", e);
        }
    }

    public Optional<Integer> findInProgressGameId() {
        String query = "SELECT id FROM GameState WHERE is_finished = FALSE ORDER BY start_time DESC LIMIT 1";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 GameId 찾기 실패", e);
        }
    }

    public Optional<GameStateDto> findGameStateFromId(int gameId) {
        String query = "SELECT turn_color, winner, is_finished FROM GameState WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, gameId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String turnColor = resultSet.getString("turn_color");
                String winner = resultSet.getString("winner");
                boolean isFinished = resultSet.getBoolean("is_finished");

                return Optional.of(new GameStateDto(turnColor, winner, isFinished));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임 상태 찾기 실패", e);
        }
    }
}

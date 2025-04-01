package dao;

import domain.TeamType;
import domain.turn.GameState;
import domain.turn.TurnState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JanggiGameDao {

    public List<Long> findInProgressGameIds(Connection connection) {
        String query = "SELECT game_id FROM janggi_games WHERE game_status = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, GameState.IN_PROGRESS.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Long> result = mapInProgressGameIds(resultSet);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public Optional<TurnState> findTurnStateById(Long gameId, Connection connection) {
        String query = "SELECT undo_last, turn FROM janggi_games WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Optional<TurnState> result = mapTurnState(resultSet);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public Optional<GameState> findGameStateById(Long gameId, Connection connection) {
        String query = "SELECT game_status FROM janggi_games WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Optional<GameState> result = mapGameState(resultSet);
            resultSet.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public long saveJanggiGame(TurnState turnState, GameState gameState, Connection connection) {
        String query = "INSERT INTO janggi_games (game_status, turn, undo_last) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, gameState.name());
            preparedStatement.setString(2, turnState.playerTeam().name());
            preparedStatement.setBoolean(3, turnState.undoLast());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            long result = generatedKeys.getLong(1);
            generatedKeys.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public int updateGameState(Long gameId, GameState gameState, Connection connection) {
        String query = "UPDATE janggi_games SET game_status = ? WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameState.name());
            preparedStatement.setLong(2, gameId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    public int updateTurnState(Long gameId, TurnState turnState, Connection connection) {
        String query = "UPDATE janggi_games SET turn = ?, undo_last = ? WHERE game_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turnState.playerTeam().name());
            preparedStatement.setBoolean(2, turnState.undoLast());
            preparedStatement.setLong(3, gameId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    private Optional<TurnState> mapTurnState(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                boolean undoLast = resultSet.getBoolean("undo_last");
                TeamType teamType = TeamType.valueOf(resultSet.getString("turn"));
                return Optional.of(new TurnState(undoLast, teamType));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    private Optional<GameState> mapGameState(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                GameState gameState = GameState.valueOf(resultSet.getString("game_status"));
                return Optional.of(gameState);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }

    private List<Long> mapInProgressGameIds(ResultSet resultSet) {
        List<Long> gameIds = new ArrayList<>();
        try {
            while (resultSet.next()) {
                gameIds.add(resultSet.getLong("game_id"));
            }
            return gameIds;
        } catch (SQLException e) {
            throw new RuntimeException("DB 접근 도중 예외가 발생했습니다.");
        }
    }
}

package janggi.dao;

import static janggi.dao.ConnectionUtils.getConnection;

import janggi.domain.piece.Dynasty;
import java.sql.SQLException;

public class GameDao {

    public Game findByStatus(Status status) {
        final var query = "SELECT * FROM game WHERE status = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, status.getSymbol());

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Game(
                        resultSet.getLong("id"),
                        Status.from(resultSet.getInt("status")),
                        Dynasty.valueOf(resultSet.getString("current_turn"))
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addGame(Game game) {
        final var query = "INSERT INTO game (status, current_turn) VALUES(?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, game.getStatus().getSymbol());
            preparedStatement.setString(2, game.getCurrentTurn().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCurrentTurn(Long gameId, Dynasty currentTurn) {
        final var query = "UPDATE game SET current_turn = ? WHERE id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, currentTurn.name());
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(Long gameId, Status status) {
        final var query = "UPDATE game SET status = ? WHERE id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, status.getSymbol());
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package janggi.dao;

import static janggi.dao.ConnectionUtils.getConnection;

import janggi.dao.entity.GameEntity;
import janggi.dao.entity.Status;
import janggi.domain.piece.Dynasty;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcGameDao implements GameDao {

    @Override
    public Optional<GameEntity> findById(Long gameId) {
        final var query = "SELECT * FROM game WHERE id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new GameEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        Status.from(resultSet.getInt("status")),
                        Dynasty.valueOf(resultSet.getString("current_turn"))
                ));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }

    @Override
    public void addGame(GameEntity gameEntity) {
        final var query = "INSERT INTO game (name, status, current_turn) VALUES(?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameEntity.getName());
            preparedStatement.setInt(2, gameEntity.getStatus().getSymbol());
            preparedStatement.setString(3, gameEntity.getCurrentTurn().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }

    @Override
    public void updateCurrentTurn(Long gameId, Dynasty currentTurn) {
        final var query = "UPDATE game SET current_turn = ? WHERE id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, currentTurn.name());
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }

    @Override
    public void updateStatus(Long gameId, Status status) {
        final var query = "UPDATE game SET status = ? WHERE id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, status.getSymbol());
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }

    @Override
    public Optional<GameEntity> findByName(String name) {
        final var query = "SELECT * FROM game WHERE name = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new GameEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        Status.from(resultSet.getInt("status")),
                        Dynasty.valueOf(resultSet.getString("current_turn"))
                ));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }
}

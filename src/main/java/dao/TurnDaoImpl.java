package dao;

import domain.type.JanggiTeam;
import entity.TurnEntity;

import java.sql.SQLException;
import java.util.Optional;

public class TurnDaoImpl implements TurnDao {
    private final DatabaseConnector databaseConnector;

    public TurnDaoImpl(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void deleteAll() {
        final var query = "TRUNCATE turn";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(TurnEntity turnEntity) {
        deleteAll();
        final String query = "INSERT INTO turn(team) VALUES(?)";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turnEntity.getTeamName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<TurnEntity> findTurn() {
        final var query = "SELECT * FROM turn";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final String teamName = resultSet.getString("team");
                TurnEntity entity = new TurnEntity(JanggiTeam.from(teamName));
                return Optional.of(entity);
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package repository;

import domain.Game;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.connect.ConnectionProvider;

public class GameRepositoryImpl implements GameRepository {

    private final ConnectionProvider connectionProvider;

    public GameRepositoryImpl(final ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void save(final Game game) {
        final String query = "INSERT INTO game (name, status) VALUES (?, ?)";
        try (final Connection connection = connectionProvider.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, game.getName());
            preparedStatement.setString(2, game.getStatus().name());
            preparedStatement.executeUpdate();
            connectionProvider.closeConnection(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasPlayingGame() {
        final String query = "SELECT COUNT(*) FROM game WHERE status = 'PLAYING'";
        try (final Connection connection = connectionProvider.getConnection();
             final var preparedStatement = connection.prepareStatement(query);
             final var resultSet = preparedStatement.executeQuery()) {
            connectionProvider.closeConnection(connection);
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<String> findGameNameAll() {
        final String query = "SELECT name FROM game";
        try (final Connection connection = connectionProvider.getConnection();
             final var preparedStatement = connection.prepareStatement(query);
             final var resultSet = preparedStatement.executeQuery()) {
            connectionProvider.closeConnection(connection);
            final List<String> gameNames = new ArrayList<>();
            while (resultSet.next()) {
                gameNames.add(resultSet.getString("name"));
            }
            return gameNames;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

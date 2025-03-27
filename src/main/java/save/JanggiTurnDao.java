package save;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import piece.player.Team;

public class JanggiTurnDao {

    private static final String CANNOT_CREATE_TABLE = "테이블을 생성하는데 실패하였습니다";

    private final MySQLConnection connection;

    public JanggiTurnDao(MySQLConnection mySQConnection) {
        this.connection = mySQConnection;
        initiateTable();
    }

    private void initiateTable() {
        final String createTableQuery = """
                    CREATE TABLE IF NOT EXISTS janggi_turn (
                        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        team VARCHAR(30) NOT NULL,
                        turn INT NOT NULL UNIQUE,
                        score INT NOT NULL
                    );
                """;

        try (Connection conn = this.connection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(createTableQuery)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SaveFailException(e);
        }
    }

    public void addTurnScore(Team team, int turn, int score) {
        final var query = "INSERT INTO janggi_turn (team, turn, score) VALUES(?, ?, ?)";
        try (final var connection = this.connection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            preparedStatement.setInt(2, turn);
            preparedStatement.setInt(3, score);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new SaveFailException(e);
        }
    }

    public Optional<Integer> getLatestTurnId() {
        final var query = "SELECT id FROM janggi_turn ORDER BY turn DESC LIMIT 1";

        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id"));
            }
        } catch (final SQLException e) {
            throw new SaveFailException(e);
        }
        return Optional.empty();
    }

    public Optional<Integer> getLatestTurn() {
        final var query = "SELECT turn FROM janggi_turn ORDER BY turn DESC LIMIT 1";

        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("turn"));
            }
        } catch (final SQLException e) {
            throw new SaveFailException(e);
        }
        return Optional.empty();

    }

    public void deleteAll() {
        final var query = "DELETE FROM janggi_turn";
        try (final var connection = this.connection.getConnection()) {
            final var preparedStatement = connection.prepareStatement(query);
            final var resultSet = preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new SaveFailException(e);
        }
    }
}

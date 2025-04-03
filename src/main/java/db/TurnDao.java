package db;

import domain.Team;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDao {

    private final DatabaseConnector databaseConnector;

    public TurnDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void changeTurn(final Team team) {
        final var updateTurnSql = "UPDATE turn SET team = ?";

        try (final var connection = databaseConnector.getConnection();
             final var turnStmt = connection.prepareStatement(updateTurnSql)) {
            turnStmt.setString(1, team.name());
            turnStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Team getTurn() {
        final var getTurnSql = "SELECT team FROM turn";

        try (final var connection = databaseConnector.getConnection();
             final var turnStmt = connection.prepareStatement(getTurnSql);
             final ResultSet resultSet = turnStmt.executeQuery()) {

            if (resultSet.next()) {
                final String team = resultSet.getString("team");
                return Team.valueOf(team.toUpperCase());
            } else {
                throw new SQLException("턴 정보가 존재하지 않습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

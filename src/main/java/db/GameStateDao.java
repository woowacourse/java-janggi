package db;

import domain.GameState;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameStateDao {

    private final DatabaseConnector databaseConnector;

    public GameStateDao(final DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public GameState getGameState() {

        final var gameStateSql = "SELECT state FROM game_state WHERE id = 1";

        try (final var connection = databaseConnector.getConnection();
             final var gameState = connection.prepareStatement(gameStateSql);
             final ResultSet resultSet = gameState.executeQuery()) {

            if (resultSet.next()) {
                final String state = resultSet.getString("state");
                return GameState.valueOf(state);
            }
        } catch (final SQLException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("데이터를 가져오는 과정에서 에러가 발생했습니다.");
    }

    public void updateGameState(final GameState gameState) {

        final var gameStateSql = "UPDATE game_state SET state = ? WHERE id = 1";

        try (final var connection = databaseConnector.getConnection();
             final var gameStateStmt = connection.prepareStatement(gameStateSql)) {
            gameStateStmt.setString(1, gameState.name());
            gameStateStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

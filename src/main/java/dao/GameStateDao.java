package dao;

public class GameStateDao {

    private static final String SELECT_TURN = "SELECT current_turn FROM game_state LIMIT 1";
    private static final String UPDATE_TURN = "UPDATE game_state SET current_turn = ?";
    private static final String INIT_TURN = "INSERT INTO game_state (current_turn) VALUES ('BLUE') ON DUPLICATE KEY UPDATE current_turn = 'BLUE'";

    private final DatabaseExecutor databaseExecutor;

    public GameStateDao(DatabaseExecutor databaseExecutor) {
        this.databaseExecutor = databaseExecutor;
    }

    public void initializeTurn() {
        databaseExecutor.executeUpdate(INIT_TURN, statement -> {
        });
    }

    public String getCurrentTurn() {
        return databaseExecutor.executeQuery(SELECT_TURN, (statement, resultSet) -> {
            if (resultSet.next()) {
                return resultSet.getString("current_turn");
            }
            return "BLUE";
        });
    }

    public void switchTurn() {
        String currentTurn = getCurrentTurn();
        String nextTurn = currentTurn.equals("BLUE") ? "RED" : "BLUE";

        databaseExecutor.executeUpdate(UPDATE_TURN, statement -> statement.setString(1, nextTurn));
    }
}

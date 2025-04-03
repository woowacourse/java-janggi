package dao;

public class GameStateDao {

    private static final String SELECT_TURN = "SELECT current_turn FROM game_state LIMIT 1";
    private static final String UPDATE_TURN = "UPDATE game_state SET current_turn = ?";
    private static final String INIT_TURN = "INSERT INTO game_state (current_turn) VALUES ('BLUE') ON DUPLICATE KEY UPDATE current_turn = 'BLUE'";

    private final Executor executor;

    public GameStateDao(Executor executor) {
        this.executor = executor;
    }

    public void initializeTurn() {
        executor.executeUpdate(INIT_TURN, statement -> {
        });
    }

    public String getCurrentTurn() {
        return executor.executeQuery(SELECT_TURN, (statement, resultSet) -> {
            if (resultSet.next()) {
                return resultSet.getString("current_turn");
            }
            return "BLUE";
        });
    }

    public void switchTurn() {
        String currentTurn = getCurrentTurn();
        String nextTurn = currentTurn.equals("BLUE") ? "RED" : "BLUE";

        executor.executeUpdate(UPDATE_TURN, statement -> statement.setString(1, nextTurn));
    }
}

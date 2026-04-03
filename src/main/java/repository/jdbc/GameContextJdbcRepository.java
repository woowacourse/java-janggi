package repository.jdbc;

import java.sql.SQLException;
import java.util.List;
import repository.RepositoryErrorMessage;
import repository.dao.GameContextDao;
import repository.entity.GameContextEntity;

public class GameContextJdbcRepository implements GameContextDao {

    private static final String INSERT_GAME_CONTEXT_SQL = "INSERT INTO game_context(current_turn_own_team, game_state) values(?, ?)";
    private static final String SELECT_GAME_CONTEXT_SQL = "SELECT * FROM game_context WHERE game_context_id = ?";
    private static final String UPDATE_GAME_CONTEXT_SQL = "UPDATE game_context SET current_turn_own_team = ?, game_state = ? WHERE game_context_id = ?";

    private static final String CREATE_GAME_CONTEXT_TABLE_SQL = "CREATE TABLE IF NOT EXISTS game_context (" +
            "game_context_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "current_turn_own_team VARCHAR(10) NOT NULL, " +
            "game_state VARCHAR(10) NOT NULL)";


    private final JdbcTemplate template;

    public GameContextJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Long save(GameContextEntity entity) throws SQLException {
        Object generatedKey = template.executeSave(
                INSERT_GAME_CONTEXT_SQL,
                entity.currentTurnOwnTeam(),
                entity.gameState()
        );
        return (Long) generatedKey;
    }

    @Override
    public GameContextEntity find(Long entityId) throws SQLException {
        List<Object> results = template.executeRead(
                SELECT_GAME_CONTEXT_SQL,
                (rs) -> new GameContextEntity(
                        rs.getLong("game_context_id"),
                        rs.getString("current_turn_own_team"),
                        rs.getString("game_state")
                ),
                entityId
        );

        validateFindResult(results);
        return (GameContextEntity) results.getFirst();
    }

    private void validateFindResult(List<Object> results) {
        if (results.isEmpty()) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_FOUND.getMessage());
        }
        if (results.size() > 1) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_SINGLE_RESULT.getMessage());
        }
    }

    @Override
    public void update(Long entityId, GameContextEntity newEntity) throws SQLException {
        template.executeCommand(
                UPDATE_GAME_CONTEXT_SQL,
                newEntity.currentTurnOwnTeam(),
                newEntity.gameState(),
                entityId
        );
    }
}

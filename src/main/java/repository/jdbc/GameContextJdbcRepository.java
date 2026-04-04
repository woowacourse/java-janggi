package repository.jdbc;

import java.sql.SQLException;
import java.util.List;
import repository.RepositoryErrorMessage;
import repository.dao.GameContextDao;
import repository.entity.GameContext;

public class GameContextJdbcRepository implements GameContextDao {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS game_contexts (" +
            "game_context_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "current_turn_own_team VARCHAR(255), " +
            "game_state VARCHAR(255))";

    private static final String INSERT_SQL = "INSERT INTO game_contexts (current_turn_own_team, game_state) VALUES (?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT game_context_id, current_turn_own_team, game_state FROM game_contexts WHERE game_context_id = ?";
    private static final String UPDATE_SQL = "UPDATE game_contexts SET current_turn_own_team = ?, game_state = ? WHERE game_context_id = ?";

    private final JdbcTemplate template;

    public GameContextJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    private void initTable() throws SQLException {
        template.executeCommand(CREATE_TABLE_SQL);
    }

    @Override
    public Long save(GameContext entity) throws SQLException {
        Object generatedId = template.executeSave(
                INSERT_SQL,
                entity.currentTurnOwnTeam(),
                entity.gameState()
        );
        return (Long) generatedId;
    }

    @Override
    public GameContext find(Long entityId) throws SQLException {
        List<GameContext> entities = template.executeRead(
                SELECT_BY_ID_SQL,
                (rs) -> new GameContext(
                        rs.getLong("game_context_id"),
                        rs.getString("current_turn_own_team"),
                        rs.getString("game_state")
                ),
                entityId
        );

        validateSingleEntity(entities);
        return entities.getFirst();
    }

    private void validateSingleEntity(List<GameContext> entities) {
        if (entities.isEmpty()) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_FOUND.getMessage());
        }
        if (entities.size() > 1) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_SINGLE_RESULT.getMessage());
        }
    }

    @Override
    public void update(Long entityId, GameContext newEntity) throws SQLException {
        template.executeCommand(
                UPDATE_SQL,
                newEntity.currentTurnOwnTeam(),
                newEntity.gameState(),
                entityId
        );
    }
}

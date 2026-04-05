package repository.jdbc;

import java.sql.Connection;
import java.util.List;
import repository.RepositoryErrorMessage;
import repository.entity.GameEntity;

public class GameJdbcRepository {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS games (" +
            "game_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "current_turn_own_team VARCHAR(255), " +
            "game_state VARCHAR(255))";

    private static final String INSERT_SQL = "INSERT INTO games (current_turn_own_team, game_state) VALUES (?, ?)";
    private static final String SELECT_BY_ID_SQL = "SELECT game_id, current_turn_own_team, game_state FROM games WHERE game_id = ?";
    private static final String SELECT_BY_STATE_SQL = "SELECT game_id, current_turn_own_team, game_state FROM games WHERE game_state = ?";
    private static final String UPDATE_SQL = "UPDATE games SET current_turn_own_team = ?, game_state = ? WHERE game_id = ?";

    private final JdbcTemplate template;

    public GameJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void initTable(Connection connection) {
        template.executeCommand(connection, CREATE_TABLE_SQL);
    }

    public Long save(Connection connection, GameEntity entity) {
        Object generatedId = template.executeSave(
                connection,
                INSERT_SQL,
                entity.turn(),
                entity.state()
        );
        return Long.parseLong(generatedId.toString());
    }

    public GameEntity find(Connection connection, Long entityId) {
        List<GameEntity> entities = template.executeRead(
                connection,
                SELECT_BY_ID_SQL,
                (rs) -> new GameEntity(
                        rs.getLong("game_id"),
                        rs.getString("current_turn_own_team"),
                        rs.getString("game_state")
                ),
                entityId
        );

        validateSingleEntity(entities);
        return entities.getFirst();
    }

    public List<GameEntity> findByState(Connection connection, String state) {
        return template.executeRead(
                connection,
                SELECT_BY_STATE_SQL,
                (rs) -> new GameEntity(
                        rs.getLong("game_id"),
                        rs.getString("current_turn_own_team"),
                        rs.getString("game_state")
                ),
                state
        );
    }

    private void validateSingleEntity(List<GameEntity> entities) {
        if (entities.isEmpty()) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_FOUND.getMessage());
        }
        if (entities.size() > 1) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_SINGLE_RESULT.getMessage());
        }
    }

    public void update(Connection connection, Long entityId, GameEntity newEntity) {
        template.executeCommand(
                connection,
                UPDATE_SQL,
                newEntity.turn(),
                newEntity.state(),
                entityId
        );
    }
}

package repository.jdbc;

import java.sql.SQLException;
import java.util.List;
import repository.RepositoryErrorMessage;
import repository.dao.GameDao;
import repository.entity.Game;
import repository.entity.GameContext;

public class GameJdbcRepository implements GameDao {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS games (" +
            "game_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "game_context_id BIGINT, " +
            "CONSTRAINT fk_game_context FOREIGN KEY (game_context_id) REFERENCES game_contexts(game_context_id) ON DELETE CASCADE)";

    private static final String INSERT_SQL = "INSERT INTO games (game_context_id) VALUES (?)";
    private static final String SELECT_BY_ID_SQL = "SELECT game_id, game_context_id FROM games WHERE game_id = ?";
    private static final String UPDATE_SQL = "UPDATE games SET game_context_id = ? WHERE game_id = ?";

    private final JdbcTemplate template;

    public GameJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    private void initTable() throws SQLException {
        template.executeCommand(CREATE_TABLE_SQL);
    }

    @Override
    public Long save(Game entity) throws SQLException {
        Object generatedId = template.executeSave(
                INSERT_SQL,
                entity.gameContextId()
        );
        return (Long) generatedId;
    }

    @Override
    public Game find(Long entityId) throws SQLException {
        List<Game> entities = template.executeRead(
                SELECT_BY_ID_SQL,
                (rs) -> new Game(
                        rs.getLong("game_id"),
                        rs.getLong("game_context_id"),
                        List.of()
                ),
                entityId
        );

        validateSingleEntity(entities);
        return entities.getFirst();
    }

    private void validateSingleEntity(List<Game> entities) {
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
                newEntity.gameContextId(),
                entityId
        );
    }
}

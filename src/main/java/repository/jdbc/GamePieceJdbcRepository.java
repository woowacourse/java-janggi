package repository.jdbc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import repository.RepositoryErrorMessage;
import repository.entity.GamePieceEntity;
import repository.jdbc.JdbcTemplate.RowMapper;

public class GamePieceJdbcRepository {

    private static final String INSERT_SQL =
            "INSERT INTO game_pieces (game_id, piece_type, team, position_row, position_col, is_active) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String FIND_BY_ID_SQL =
            "SELECT game_piece_id, game_id, piece_type, team, position_row, position_col, is_active " +
                    "FROM game_pieces " +
                    "WHERE game_piece_id = ?";

    private static final String FIND_BY_GAME_ID_SQL =
            "SELECT game_piece_id, game_id, piece_type, team, position_row, position_col, is_active " +
                    "FROM game_pieces " +
                    "WHERE game_id = ?";

    private static final String FIND_ALL_SQL =
            "SELECT game_piece_id, game_id, piece_type, team, position_row, position_col, is_active " +
                    "FROM game_pieces";

    private static final String UPDATE_SQL =
            "UPDATE game_pieces " +
                    "SET position_row = ?, position_col = ?, is_active = ? " +
                    "WHERE game_piece_id = ?";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS game_pieces (" +
                    "game_piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "game_id BIGINT NOT NULL, " +
                    "piece_type VARCHAR(10) NOT NULL, " +
                    "team VARCHAR(10) NOT NULL, " +
                    "position_row INT NOT NULL, " +
                    "position_col INT NOT NULL, " +
                    "is_active BOOLEAN DEFAULT TRUE, " +
                    "CONSTRAINT fk_gp_game FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE" +
                    ")";

    private final JdbcTemplate template;

    public GamePieceJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void initTable(Connection connection) {
        template.executeCommand(connection, CREATE_TABLE_SQL);
    }

    public Long save(Connection connection, GamePieceEntity entity) {
        Object generatedId = template.executeSave(
                connection,
                INSERT_SQL,
                entity.gameId(),
                entity.pieceType(),
                entity.team(),
                entity.row(),
                entity.col(),
                entity.isActive()
        );
        return Long.parseLong(generatedId.toString());
    }

    public List<Long> saveAll(Connection connection, List<GamePieceEntity> entities) {
        List<List<Object>> totalEntityValues = new ArrayList<>();
        for (GamePieceEntity entity : entities) {
            addSingleEntityValueForBatchSave(entity, totalEntityValues);
        }

        List<Object> generatedKeys = template.executeBatchSave(connection, INSERT_SQL, totalEntityValues);

        return generatedKeys
                .stream()
                .map(id -> Long.parseLong(id.toString()))
                .toList();
    }

    private void addSingleEntityValueForBatchSave(GamePieceEntity entity, List<List<Object>> totalEntityValues) {
        List<Object> rowValues = List.of(
                entity.gameId(),
                entity.pieceType(),
                entity.team(),
                entity.row(),
                entity.col(),
                entity.isActive()
        );
        totalEntityValues.add(rowValues);
    }

    public GamePieceEntity find(Connection connection, Long id) {
        List<GamePieceEntity> entities = template.executeRead(
                connection,
                FIND_BY_ID_SQL,
                parseRowValueToGamePieceEntity(),
                id
        );

        validateSingleEntity(entities);
        return entities.getFirst();
    }

    public List<GamePieceEntity> findByGameId(Connection connection, Long gameId) {
        return template.executeRead(
                connection,
                FIND_BY_GAME_ID_SQL,
                parseRowValueToGamePieceEntity(),
                gameId
        );
    }

    private void validateSingleEntity(List<GamePieceEntity> entities) {
        if (entities.isEmpty()) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_FOUND.getMessage());
        }
        if (entities.size() > 1) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_SINGLE_RESULT.getMessage());
        }
    }

    public List<GamePieceEntity> findAll(Connection connection) {
        return template.executeRead(
                connection,
                FIND_ALL_SQL,
                parseRowValueToGamePieceEntity()
        );
    }

    private RowMapper<GamePieceEntity> parseRowValueToGamePieceEntity() {
        return (resultSet) -> new GamePieceEntity(
                resultSet.getLong("game_piece_id"),
                resultSet.getLong("game_id"),
                resultSet.getString("piece_type"),
                resultSet.getString("team"),
                resultSet.getInt("position_row"),
                resultSet.getInt("position_col"),
                resultSet.getBoolean("is_active")
        );
    }

    public void updateAll(Connection connection, List<GamePieceEntity> newEntities) {
        List<List<Object>> totalEntityValues = new ArrayList<>();
        for (GamePieceEntity newEntity : newEntities) {
            addSingleEntityValueForUpdate(newEntity, totalEntityValues);
        }

        template.executeBatchSave(connection, UPDATE_SQL, totalEntityValues);
    }

    private void addSingleEntityValueForUpdate(GamePieceEntity newEntity, List<List<Object>> totalEntityValues) {
        List<Object> rowValues = List.of(
                newEntity.row(),
                newEntity.col(),
                newEntity.isActive(),
                newEntity.id()
        );
        totalEntityValues.add(rowValues);
    }
}

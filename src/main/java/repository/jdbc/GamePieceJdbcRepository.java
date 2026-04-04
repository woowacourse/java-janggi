package repository.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.RepositoryErrorMessage;
import repository.dao.GamePieceDao;
import repository.entity.GamePiece;

public class GamePieceJdbcRepository implements GamePieceDao {

    private static final String INSERT_SQL =
            "INSERT INTO game_pieces (game_id, piece_id, position_row, position_col, is_active) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String FIND_BY_ID_SQL =
            "SELECT game_piece_id, game_id, piece_id, position_row, position_col, is_active " +
                    "FROM game_pieces " +
                    "WHERE game_piece_id = ?";

    private static final String FIND_ALL_SQL =
            "SELECT game_piece_id, game_id, piece_id, position_row, position_col, is_active " +
                    "FROM game_pieces";

    private static final String UPDATE_SQL =
            "UPDATE game_pieces " +
                    "SET position_row = ?, position_col = ?, is_active = ? " +
                    "WHERE game_piece_id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM game_pieces WHERE game_piece_id = ?";

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS game_pieces (" +
                    "game_piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "game_id BIGINT NOT NULL, " +
                    "piece_id BIGINT NOT NULL, " +
                    "position_row INT NOT NULL, " +
                    "position_col INT NOT NULL, " +
                    "is_active BOOLEAN DEFAULT TRUE, " +
                    "CONSTRAINT fk_gp_game FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE, " +
                    "CONSTRAINT fk_gp_piece FOREIGN KEY (piece_id) REFERENCES pieces(piece_id) ON DELETE CASCADE" +
                    ")";

    private final JdbcTemplate template;

    public GamePieceJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void initTable() {
        try {
            template.executeCommand(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(GamePiece entity) {
        try {
            Object generatedId = template.executeSave(
                    INSERT_SQL,
                    entity.gameId(),
                    entity.pieceId(),
                    entity.row(),
                    entity.col(),
                    entity.isActive()
            );
            return (Long) generatedId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> saveAll(List<GamePiece> entities) {
        try {
            List<List<Object>> totalEntityValues = new ArrayList<>();
            for (GamePiece entity : entities) {
                List<Object> rowValues = new ArrayList<>();
                rowValues.add(entity.gameId());
                rowValues.add(entity.pieceId());
                rowValues.add(entity.row());
                rowValues.add(entity.col());
                rowValues.add(entity.isActive());
                totalEntityValues.add(rowValues);
            }

            List<Object> generatedKeys = template.executeBatchSave(INSERT_SQL, totalEntityValues);
            return generatedKeys.stream()
                .map(id -> (Long) id)
                .toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GamePiece find(Long id) {
        try {
            List<GamePiece> entities = template.executeRead(
                    FIND_BY_ID_SQL,
                    (rs) -> new GamePiece(
                            rs.getLong("game_piece_id"),
                            rs.getLong("game_id"),
                            rs.getLong("piece_id"),
                            rs.getInt("position_row"),
                            rs.getInt("position_col"),
                            rs.getBoolean("is_active")
                    ),
                    id
            );

            validateSingleEntity(entities);
            return entities.getFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateSingleEntity(List<GamePiece> entities) {
        if (entities.isEmpty()) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_FOUND.getMessage());
        }
        if (entities.size() > 1) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_SINGLE_RESULT.getMessage());
        }
    }

    @Override
    public List<GamePiece> findAll() {
        try {
            return template.executeRead(
                    FIND_ALL_SQL,
                    (rs) -> new GamePiece(
                            rs.getLong("game_piece_id"),
                            rs.getLong("game_id"),
                            rs.getLong("piece_id"),
                            rs.getInt("position_row"),
                            rs.getInt("position_col"),
                            rs.getBoolean("is_active")
                    )
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GamePiece newEntity) {
        try {
            template.executeCommand(
                    UPDATE_SQL,
                    newEntity.row(),
                    newEntity.col(),
                    newEntity.isActive(),
                    newEntity.gamePieceId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

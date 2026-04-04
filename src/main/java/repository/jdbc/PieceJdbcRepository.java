package repository.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.RepositoryErrorMessage;
import repository.dao.PieceDao;
import repository.entity.Piece;

public class PieceJdbcRepository implements PieceDao {

    private static final String INSERT_PIECE_SQL = "INSERT INTO pieces(team, type) values(?, ?)";
    private static final String SELECT_PIECE_SQL = "SELECT * FROM pieces WHERE piece_id = ?";
    private static final String SELECT_PIECES_SQL = "SELECT * FROM pieces";

    private static final String CREATE_PIECE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS pieces (" +
            "piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "team VARCHAR(10) NOT NULL, " +
            "type VARCHAR(10) NOT NULL)";

    private final JdbcTemplate template;

    public PieceJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void initTable(Connection connection) throws SQLException {
        template.executeCommand(connection, CREATE_PIECE_TABLE_SQL);
    }

    @Override
    public Long save(Connection connection, Piece entity) throws SQLException {
        Object generatedId = template.executeSave(
                connection,
                INSERT_PIECE_SQL,
                entity.pieceType(),
                entity.team()
        );
        return (Long) generatedId;
    }

    @Override
    public List<Long> saveAll(Connection connection, List<Piece> entities) throws SQLException {
        List<List<Object>> totalEntityValues = new ArrayList<>();
        for (Piece entity : entities) {
            List<Object> rowValues = new ArrayList<>();
            rowValues.add(entity.team());
            rowValues.add(entity.pieceType());

            totalEntityValues.add(rowValues);
        }

        List<Object> generatedKeys = template.executeBatchSave(connection, INSERT_PIECE_SQL, totalEntityValues);
        return generatedKeys.stream()
                .map(id -> (Long) id)
                .toList();
    }

    @Override
    public Piece find(Connection connection, Long id) throws SQLException {
        List<Piece> pieceEntities = template.executeRead(
                connection,
                SELECT_PIECE_SQL,
                (rs) -> new Piece(
                        rs.getLong("piece_id"),
                        rs.getString("team"),
                        rs.getString("type")
                ),
                id
        );

        validateSinglePieceEntity(pieceEntities);
        return pieceEntities.getFirst();
    }

    private void validateSinglePieceEntity(List<Piece> pieceEntities) {
        if (pieceEntities.isEmpty()) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_FOUND.getMessage());
        }
        if (pieceEntities.size() > 1) {
            throw new IllegalStateException(RepositoryErrorMessage.NOT_SINGLE_RESULT.getMessage());
        }
    }

    @Override
    public List<Piece> findAll(Connection connection) throws SQLException {
        return template.executeRead(
                connection,
                SELECT_PIECES_SQL,
                (rs) -> new Piece(
                        rs.getLong("piece_id"),
                        rs.getString("team"),
                        rs.getString("type")
                )
        );
    }
}

package repository.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import repository.dao.PieceSaveDao;
import repository.entity.PieceEntity;

public class PieceJdbcRepository implements PieceSaveDao {

    private static final String INSERT_PIECE_SQL = "INSERT INTO piece(piece_row, piece_col, team, type ) values(?, ?, ?, ?)";
    private static final String CREATE_PIECE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS piece (" +
            "piece_id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "piece_row INT NOT NULL, " +
            "piece_col INT NOT NULL, " +
            "team VARCHAR(10) NOT NULL, " +
            "type VARCHAR(10) NOT NULL)";


    private final JdbcTemplate template;

    public PieceJdbcRepository(JdbcTemplate template) {
        this.template = template;
    }

    private void initTable() throws SQLException {
        template.executeCommand(CREATE_PIECE_TABLE_SQL);
    }


    @Override
    public void save(PieceEntity entity) throws SQLException {
        template.executeCommand(
                INSERT_PIECE_SQL,
                entity.row(),
                entity.col(),
                entity.pieceType(),
                entity.team()
        );
    }

    @Override
    public void saveAll(List<PieceEntity> entities) throws SQLException {
        List<List<Object>> totalEntityValues = new ArrayList<>();
        for (PieceEntity entity : entities) {
            List<Object> rowValues = new ArrayList<>();
            rowValues.add(entity.row());
            rowValues.add(entity.col());
            rowValues.add(entity.team());
            rowValues.add(entity.pieceType());

            totalEntityValues.add(rowValues);
        }

        template.executeBatchCommand(INSERT_PIECE_SQL, totalEntityValues);
    }
}

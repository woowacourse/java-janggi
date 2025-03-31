package infra.dao;

import infra.entity.PieceEntity;
import java.util.List;
import utils.DBUtils;

public class PieceDao {

    public PieceEntity save(final PieceEntity pieceEntity) {
        final String query = "INSERT INTO piece (dtype, team, column_index, row_index) VALUES (?, ?, ?, ?)";
        final Long id = DBUtils.executeInsert(query, pieceEntity.getDtype(), pieceEntity.getTeam(),
            pieceEntity.getColumnIndex(), pieceEntity.getRowIndex());

        return new PieceEntity(id, pieceEntity.getDtype(), pieceEntity.getTeam(),
            pieceEntity.getColumnIndex(), pieceEntity.getRowIndex());
    }

    public boolean exists() {
        final String query = "SELECT EXISTS(SELECT 1 FROM piece LIMIT 1)";

        return Boolean.TRUE.equals(
            DBUtils.executeQuery(query, resultSet -> resultSet.getBoolean(1)));
    }

    public List<PieceEntity> findAll() {
        final String query = "SELECT id, dtype, team, column_index, row_index FROM piece";

        return DBUtils.executeQueryList(query, resultSet -> new PieceEntity(
            resultSet.getLong("id"),
            resultSet.getString("dtype"),
            resultSet.getString("team"),
            resultSet.getInt("column_index"),
            resultSet.getInt("row_index")
        ));
    }

    public void deleteAll() {
        DBUtils.executeUpdate("DELETE FROM piece");
    }
}

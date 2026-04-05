package repository.mapper;

import repository.dao.PieceDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PieceDaoMapper implements RowMapper<PieceDao> {
    @Override
    public PieceDao mapRow(ResultSet resultSet) throws SQLException {
        return new PieceDao(
                resultSet.getString("piece_type"),
                resultSet.getString("team"),
                resultSet.getInt("row_idx"),
                resultSet.getInt("col_idx")
        );
    }
}

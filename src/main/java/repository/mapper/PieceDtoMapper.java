package repository.mapper;

import model.game.dto.PieceDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PieceDtoMapper implements RowMapper<PieceDto> {
    @Override
    public PieceDto mapRow(ResultSet resultSet) throws SQLException {
        return new PieceDto(
                resultSet.getString("piece_type"),
                resultSet.getString("team"),
                resultSet.getInt("row_idx"),
                resultSet.getInt("col_idx")
        );
    }
}

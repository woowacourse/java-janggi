package repository.mapper;

import model.game.dto.PieceDto;
import repository.column.PieceColumn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PieceDtoMapper implements RowMapper<PieceDto> {
    @Override
    public PieceDto mapRow(ResultSet resultSet) throws SQLException {
        return new PieceDto(
                resultSet.getString(PieceColumn.PIECE_TYPE),
                resultSet.getString(PieceColumn.TEAM),
                resultSet.getInt(PieceColumn.ROW_IDX),
                resultSet.getInt(PieceColumn.COL_IDX)
        );
    }
}

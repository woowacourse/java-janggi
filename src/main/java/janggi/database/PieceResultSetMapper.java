package janggi.database;

import janggi.piece.PieceDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PieceResultSetMapper {
    public static PieceDto mapToDto(ResultSet resultSet) throws SQLException {
        return new PieceDto(
                resultSet.getInt("x"),
                resultSet.getInt("y"),
                resultSet.getString("piece_type"),
                resultSet.getString("color")
        );
    }

    public static List<PieceDto> mapToDtos(final ResultSet resultSet) throws SQLException {
        final List<PieceDto> pieceDtos = new java.util.ArrayList<>();
        while (resultSet.next()) {
            pieceDtos.add(mapToDto(resultSet));
        }
        return pieceDtos;
    }
}

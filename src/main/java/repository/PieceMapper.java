package repository;

import domain.Side;
import domain.coordinate.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.PieceTypeMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceMapper {

    public static Map<Position, Piece> toPositionPiece(ResultSet resultSet) throws SQLException {
        Map<Position, Piece> pieces = new HashMap<>();
        while (resultSet.next()) {
            int colNum = resultSet.getInt("col_num");
            int rowNum = resultSet.getInt("row_num");
            String pieceType = resultSet.getString("piece_type");
            String side = resultSet.getString("side");

            Position position = new Position(colNum, rowNum);
            Piece piece = PieceTypeMapper.create(PieceType.valueOf(pieceType), Side.valueOf(side));
            pieces.put(position, piece);
        }
        return pieces;
    }
}

package repository;

import domain.Side;
import domain.coordinate.Position;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.EmptyPiece;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceType;

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
            Piece piece = createPiece(PieceType.valueOf(pieceType), Side.valueOf(side));
            pieces.put(position, piece);
        }
        return pieces;
    }

    private static Piece createPiece(PieceType type, Side side) {
        return switch (type) {
            case KING -> new King(side);
            case GUARD -> new Guard(side);
            case CHARIOT -> new Chariot(side);
            case HORSE -> new Horse(side);
            case ELEPHANT -> new Elephant(side);
            case CANNON -> new Cannon(side);
            case PAWN -> new Pawn(side);
            case EMPTY -> EmptyPiece.getInstance();
        };
    }
}

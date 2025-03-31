package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import board.Position;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.Guard;
import piece.Horse;
import piece.King;
import piece.Piece;
import piece.PieceType;
import piece.Soldier;

public class PieceConverter {

    public static PieceEntity toEntity(final Position position, final Piece piece) {
        return new PieceEntity(null, position.getRow(), position.getColumn(), piece.getType(), piece.getTeam());
    }

    public static Map<Position, Piece> toPieces(final List<PieceEntity> pieceEntities) {
        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceEntity pieceEntity : pieceEntities) {
            pieces.put(new Position(pieceEntity.rowValue(), pieceEntity.columnValue()), toPiece(pieceEntity));
        }
        return pieces;
    }

    private static Piece toPiece(final PieceEntity pieceEntity) {
        if (pieceEntity.pieceType() == PieceType.KING) {
            return new King(pieceEntity.team());
        }
        if (pieceEntity.pieceType() == PieceType.CANNON) {
            return new Cannon(pieceEntity.team());
        }
        if (pieceEntity.pieceType() == PieceType.CHARIOT) {
            return new Chariot(pieceEntity.team());
        }
        if (pieceEntity.pieceType() == PieceType.ELEPHANT) {
            return new Elephant(pieceEntity.team());
        }
        if (pieceEntity.pieceType() == PieceType.GUARD) {
            return new Guard(pieceEntity.team());
        }
        if (pieceEntity.pieceType() == PieceType.HORSE) {
            return new Horse(pieceEntity.team());
        }
        return new Soldier(pieceEntity.team());
    }

}

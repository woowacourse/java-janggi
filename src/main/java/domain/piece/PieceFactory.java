package domain.piece;

import domain.state.Side;

public class PieceFactory {

    public static Piece create(String typeName, String sideName) {
        PieceType type = PieceType.valueOf(typeName);
        Side side = Side.valueOf(sideName);

        return switch (type) {
            case CHARIOT -> new Chariot(side);
            case CANNON -> new Cannon(side);
            case HORSE -> new Horse(side);
            case ELEPHANT -> new Elephant(side);
            case GUARD -> new Guard(side);
            case KING -> new King(side);
            case PAWN -> new Pawn(side);
            case EMPTY -> EmptyPiece.getInstance();
        };
    }
}

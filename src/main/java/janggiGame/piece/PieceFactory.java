package janggiGame.piece;

import janggiGame.piece.curveMovePiece.Elephant;
import janggiGame.piece.curveMovePiece.Horse;
import janggiGame.piece.oneMovePiece.Advisor;
import janggiGame.piece.oneMovePiece.King;
import janggiGame.piece.oneMovePiece.Pawn;
import janggiGame.piece.straightMovePiece.Cannon;
import janggiGame.piece.straightMovePiece.Chariot;

public class PieceFactory {
    public static Piece createPieceOf(Type type, Dynasty dynasty) {
        return switch (type) {
            case KING -> new King(dynasty);
            case ADVISOR -> new Advisor(dynasty);
            case ELEPHANT -> new Elephant(dynasty);
            case HORSE -> new Horse(dynasty);
            case CHARIOT -> new Chariot(dynasty);
            case CANNON -> new Cannon(dynasty);
            case PAWN -> new Pawn(dynasty);
        };
    }
}

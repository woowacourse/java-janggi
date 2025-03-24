package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;

public interface Piece {

    PieceType getType();

    List<Position> computeReachableDestinations(final Position position, final Map<Position, Piece> board);

    boolean isHan();

    boolean isCho();

    default boolean isAlly(Piece piece) {
        if (isCho()) {
            return piece.isCho();
        }
        if (isHan()) {
            return piece.isHan();
        }
        return false;
    }

    default boolean isOccupied() {
        return !(getType() == PieceType.EMPTY);
    }

    default boolean isCannon() {
        return getType() == PieceType.CANNON;
    }

    default boolean isGameOver() {
        return getType() == PieceType.KING;
    }
}

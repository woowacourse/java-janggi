package janggi.domain.piece;

import janggi.domain.Turn;
import janggi.domain.board.Position;

import java.util.List;
import java.util.Map;

public interface Piece {

    PieceType getType();

    List<Position> computeReachableDestinations(final Position position, final Map<Position, Piece> board);

    boolean isHan();

    boolean isCho();

    Turn getTurn();

    default boolean isAlly(Piece piece) {
        return getTurn() == piece.getTurn();
    }

    default boolean isOccupied() {
        return !(getType() == PieceType.EMPTY);
    }

    default boolean isCannon() {
        return getType() == PieceType.CANNON;
    }

    default boolean isKing() {
        return getType() == PieceType.KING;
    }

    default boolean isSameSide(final Turn turn) {
        return turn == getTurn();
    }
}

package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import java.util.Set;

public interface PieceBehavior {

    Set<Position> generateAvailableMovePositions(Board board, Side side, Position position);

    String toName();

    default boolean isCannon() {
        return false;
    }

    default boolean isGeneral() {
        return false;
    }
}
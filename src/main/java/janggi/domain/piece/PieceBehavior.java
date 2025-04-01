package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import java.util.Set;

public interface PieceBehavior {

    Set<Position> generateAvailableMovePositions(BoardPositionInfo boardPositionInfo);

    String toName();

    int toScore();

    default boolean isCannon() {
        return false;
    }

    default boolean isGeneral() {
        return false;
    }
}

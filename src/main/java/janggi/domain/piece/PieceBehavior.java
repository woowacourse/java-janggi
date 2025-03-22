package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import java.util.Set;

public interface PieceBehavior {

    Set<Position> generateAvailableMovePositions(Board board, Side side, Position position);

    String toName();
}

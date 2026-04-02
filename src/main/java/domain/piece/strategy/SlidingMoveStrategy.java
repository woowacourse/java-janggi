package domain.piece.strategy;

import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.List;

public class SlidingMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        if (start.isSameRow(destination)) {
            return start.getHorizontalPathExcludeDestination(destination);
        }
        if (start.isSameColumn(destination)) {
            return start.getVerticalPathExcludeDestination(destination);
        }
        throw new IllegalArgumentException(PieceExceptionMessage.INVALID_POSITION.getMessage());
    }
}

package domain.piece.strategy;

import domain.piece.MoveErrorMessage;
import domain.position.Position;
import java.util.List;

public class SlidingMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        if (start.isSameRow(destination.getRow())) {
            return start.getSameRowPositionsToDestination(destination);
        }
        if (start.isSameColumn(destination.getColumn())) {
            return start.getSameColumnPositionsToDestination(destination);
        }
        throw new IllegalArgumentException(MoveErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage());
    }
}

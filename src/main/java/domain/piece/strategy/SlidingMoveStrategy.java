package domain.piece.strategy;

import common.ErrorMessage;
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
        throw new IllegalArgumentException(ErrorMessage.INVALID_POS_INPUT.getMessage());
    }
}

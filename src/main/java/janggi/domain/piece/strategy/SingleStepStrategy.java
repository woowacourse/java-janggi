package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class SingleStepStrategy implements MoveStrategy {

    private static final int SINGLE_STEP_DISTANCE = 1;

    private final boolean forwardOnly;

    public SingleStepStrategy(boolean forwardOnly) {
        this.forwardOnly = forwardOnly;
    }

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation direction = new DirectionInformation(source, destination);
        validateMovement(direction);

        if (forwardOnly) {
            validateForwardDirection(direction.calculateRowDirection(), camp);
        }
        return List.of(destination);
    }

    private void validateForwardDirection(int rowDirection, Camp camp) {
        boolean isRowMove = rowDirection != 0;
        boolean isForward = camp.matchesForwardDirection(rowDirection);

        if (isRowMove && !isForward) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }
    }

    private void validateMovement(DirectionInformation direction) {
        if (direction.calculateDistance() != SINGLE_STEP_DISTANCE) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }
}

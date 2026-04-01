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
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        if (forwardOnly) {
            camp.validateForwardDirection(directionInformation.calculateRowDirection());
            validateMovement(directionInformation);
            return List.of(destination);
        }
        validateMovement(directionInformation);
        return List.of(destination);
    }

    private void validateMovement(DirectionInformation directionInfo) {
        if (directionInfo.calculateDistance() != SINGLE_STEP_DISTANCE) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }
}

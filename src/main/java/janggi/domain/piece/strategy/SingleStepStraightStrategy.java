package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class SingleStepStraightStrategy implements MoveStrategy {

    public static final int SINGLE_STEP_DISTANCE = 1;

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);
        validateSingleStepMovement(directionInformation);

        return List.of(destination);
    }

    private void validateSingleStepMovement(DirectionInformation directionInformation) {
        if (directionInformation.calculateAbsRowDifference()
                + directionInformation.calculateAbsColumnDifference() != SINGLE_STEP_DISTANCE) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_STRAIGHT_MOVE.getMessage());
        }
    }
}

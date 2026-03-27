package janggi.domain.piece.strategy;

import static janggi.constant.GameRule.SINGLE_STEP_DISTANCE;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class SingleStepStraightStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(from, to);
        validateSingleStepMovement(directionInformation);

        return List.of(to);
    }

    private void validateSingleStepMovement(DirectionInformation directionInformation) {
        if (directionInformation.calculateAbsRowDifference()
                + directionInformation.calculateAbsColDifference() != SINGLE_STEP_DISTANCE) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_STRAIGHT_MOVE.getMessage());
        }
    }
}

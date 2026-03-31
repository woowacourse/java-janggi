package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class SoldierStrategy implements MoveStrategy {

    private static final int SINGLE_STEP_DISTANCE = 1;

    @Override
    public List<Position> findPath(Position source, Position destination, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(source, destination);

        camp.validateForwardDirection(directionInformation.calculateRowDirection());
        validateSoldierMovement(directionInformation);

        return List.of(destination);
    }

    private void validateSoldierMovement(DirectionInformation directionInformation) {
        if (directionInformation.calculateAbsRowDifference()
                + directionInformation.calculateAbsColDifference() != SINGLE_STEP_DISTANCE) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SOLDIER_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }
}

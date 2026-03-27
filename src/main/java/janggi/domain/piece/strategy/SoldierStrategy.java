package janggi.domain.piece.strategy;

import static janggi.constant.GameRule.SINGLE_STEP_DISTANCE;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.exception.ExceptionMessage;
import java.util.List;

public class SoldierStrategy implements MoveStrategy {

    @Override
    public List<Position> findPath(Position from, Position to, Camp camp) {
        DirectionInformation directionInformation = new DirectionInformation(from, to);

        camp.validateForwardDirection(directionInformation.calculateRowDirection());
        validateSoldierMovement(directionInformation);

        return List.of(to);
    }

    private void validateSoldierMovement(DirectionInformation directionInformation) {
        if (directionInformation.calculateAbsRowDifference()
                + directionInformation.calculateAbsColDifference() != SINGLE_STEP_DISTANCE) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SOLDIER_MOVE.getMessage());
        }
    }
}

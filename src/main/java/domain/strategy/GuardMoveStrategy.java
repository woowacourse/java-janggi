package domain.strategy;

import domain.Position;
import java.util.List;

public class GuardMoveStrategy extends MoveStrategy {

    private static final String ERROR_GUARD_CAN_MOVE_ONLY_WITHIN_PALACE = "[ERROR] 사는 궁성 내부에서만 움직일 수 있다.";

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        if (isPalace(destination)) {
            return createDestinations(currentPosition).contains(destination);
        }
        return false;
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        return true;
    }

    private boolean isPalace(Position destination) {
        if (!palace.isPalaceRedArea(destination) && !palace.isPalaceGreenArea(destination)) {
            throw new IllegalArgumentException(ERROR_GUARD_CAN_MOVE_ONLY_WITHIN_PALACE);
        }
        return true;
    }

    private List<Position> createDestinations(Position currentPosition) {
        return List.of(
                currentPosition.right(),
                currentPosition.down(),
                currentPosition.up(),
                currentPosition.left()
        );
    }
}

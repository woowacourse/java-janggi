package domain.piece.routestrategy;

import domain.Pattern;
import domain.position.JanggiPosition;
import java.util.Collections;
import java.util.List;

public class LinearRouteStrategy implements JanggiPieceRouteStrategy {

    @Override
    public List<Pattern> getRoute(final List<List<Pattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        for (List<Pattern> route : routes) {
            Pattern direction = route.getFirst();
            if (isValidDirection(beforePosition, afterPosition, direction)) {
                return createPattern(direction, getMoveCount(beforePosition, afterPosition, direction));
            }
        }
        throw new IllegalStateException("해당 말은 해당 경로로 이동할 수 없습니다.");
    }

    private boolean isValidDirection(
            final JanggiPosition beforePosition,
            final JanggiPosition afterPosition,
            final Pattern direction
    ) {
        JanggiPosition newPosition = beforePosition;
        while (newPosition.canMoveOnePosition(direction)) {
            newPosition = newPosition.moveOnePosition(direction);
            if (newPosition.equals(afterPosition)) {
                return true;
            }
        }
        return false;
    }

    private int getMoveCount(
            final JanggiPosition beforePosition,
            final JanggiPosition afterPosition,
            final Pattern direction
    ) {
        int moveCount = 0;
        JanggiPosition newPosition = beforePosition;
        while (newPosition.canMoveOnePosition(direction)) {
            newPosition = newPosition.moveOnePosition(direction);
            moveCount++;
            if (newPosition.equals(afterPosition)) {
                return moveCount;
            }
        }
        return moveCount;
    }

    private List<Pattern> createPattern(final Pattern direction, int additionalSize) {
        return Collections.nCopies(additionalSize, direction);
    }
}

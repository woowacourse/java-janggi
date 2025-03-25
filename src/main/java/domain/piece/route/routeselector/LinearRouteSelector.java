package domain.piece.route.routeselector;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;
import java.util.Collections;
import java.util.List;

public class LinearRouteSelector implements JanggiPieceRouteSelector {

    @Override
    public List<MovingPattern> getRoute(final JanggiSide side, final List<List<MovingPattern>> routes, final JanggiPosition beforePosition, final JanggiPosition afterPosition) {
        for (List<MovingPattern> route : routes) {
            MovingPattern direction = route.getFirst();
            if (isValidDirection(beforePosition, afterPosition, direction)) {
                return createRouteOfDirection(direction, getMoveCount(beforePosition, afterPosition, direction));
            }
        }
        throw new InvalidPathException();
    }

    private boolean isValidDirection(
            final JanggiPosition beforePosition,
            final JanggiPosition afterPosition,
            final MovingPattern direction
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
            final MovingPattern direction
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

    private List<MovingPattern> createRouteOfDirection(final MovingPattern direction, int additionalSize) {
        return Collections.nCopies(additionalSize, direction);
    }
}

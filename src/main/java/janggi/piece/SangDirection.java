package janggi.piece;

import janggi.value.JanggiPosition;
import java.util.List;

public enum SangDirection {
    LEFT(List.of(new JanggiPosition(-1, 0), new JanggiPosition(-2, -1), new JanggiPosition(-2, 1)),
            List.of(new JanggiPosition(-3, -2), new JanggiPosition(-3, 2))),
    RIGHT(List.of(new JanggiPosition(1, 0), new JanggiPosition(2, -1), new JanggiPosition(2, 1)),
            List.of(new JanggiPosition(3, -2), new JanggiPosition(3, 2))),
    UP(List.of(new JanggiPosition(0, -1), new JanggiPosition(-1, -2), new JanggiPosition(1, -2)),
            List.of(new JanggiPosition(-2, -3), new JanggiPosition(2, -3))),
    DOWN(List.of(new JanggiPosition(0, 1), new JanggiPosition(-1, 2), new JanggiPosition(1, 2)),
            List.of(new JanggiPosition(-2, 3), new JanggiPosition(2, 3))),
    NONE(List.of(), List.of()),
    ;

    private final List<JanggiPosition> routeJanggiPositions;
    private final List<JanggiPosition> destinationJanggiPositions;

    SangDirection(final List<JanggiPosition> routeJanggiPositions, final List<JanggiPosition> destinationJanggiPositions) {
        this.routeJanggiPositions = routeJanggiPositions;
        this.destinationJanggiPositions = destinationJanggiPositions;
    }

    public static SangDirection of(final JanggiPosition current, final JanggiPosition destination) {
        int xDistance = destination.getX() - current.getX();
        int yDistance = destination.getY() - current.getY();

        for (SangDirection sangDirection : SangDirection.values()) {
            List<JanggiPosition> destinationJanggiPositions = sangDirection.destinationJanggiPositions;
            boolean isValidDirection = destinationJanggiPositions.stream()
                    .anyMatch(position -> position.equals(new JanggiPosition(xDistance, yDistance)));

            if (isValidDirection) {
                return sangDirection;
            }
        }
        return NONE;
    }

    public boolean isRoute(JanggiPosition current, JanggiPosition janggiPosition) {
        for (JanggiPosition routeJanggiPosition : routeJanggiPositions) {
            JanggiPosition newJanggiPosition = new JanggiPosition(current.getX() + routeJanggiPosition.getX(),
                    current.getY() + routeJanggiPosition.getY());
            if (newJanggiPosition.equals(janggiPosition)) {
                return true;
            }
        }
        return false;
    }

}

package janggi.piece;

import janggi.value.JanggiPosition;
import janggi.value.RelativePosition;
import java.util.List;

public enum SangDirection {
    LEFT(List.of(new RelativePosition(-1, 0), new RelativePosition(-2, -1), new RelativePosition(-2, 1)),
            List.of(new RelativePosition(-3, -2), new RelativePosition(-3, 2))),
    RIGHT(List.of(new RelativePosition(1, 0), new RelativePosition(2, -1), new RelativePosition(2, 1)),
            List.of(new RelativePosition(3, -2), new RelativePosition(3, 2))),
    UP(List.of(new RelativePosition(0, -1), new RelativePosition(-1, -2), new RelativePosition(1, -2)),
            List.of(new RelativePosition(-2, -3), new RelativePosition(2, -3))),
    DOWN(List.of(new RelativePosition(0, 1), new RelativePosition(-1, 2), new RelativePosition(1, 2)),
            List.of(new RelativePosition(-2, 3), new RelativePosition(2, 3))),
    NONE(List.of(), List.of()),
    ;

    private final List<RelativePosition> routeJanggiPositions;
    private final List<RelativePosition> destinationJanggiPositions;

    SangDirection(final List<RelativePosition> routeJanggiPositions, final List<RelativePosition> destinationJanggiPositions) {
        this.routeJanggiPositions = routeJanggiPositions;
        this.destinationJanggiPositions = destinationJanggiPositions;
    }

    public static SangDirection of(final JanggiPosition current, final JanggiPosition destination) {
        int xDistance = destination.getX() - current.getX();
        int yDistance = destination.getY() - current.getY();

        for (SangDirection sangDirection : SangDirection.values()) {
            boolean isValidDirection = sangDirection.destinationJanggiPositions.stream()
                    .anyMatch(position -> position.equals(new RelativePosition(xDistance, yDistance)));

            if (isValidDirection) {
                return sangDirection;
            }
        }
        return NONE;
    }

    public boolean isRoute(JanggiPosition current, JanggiPosition janggiPosition) {
        for (RelativePosition relativePosition : routeJanggiPositions) {
            JanggiPosition newJanggiPosition = new JanggiPosition(current.getX() + relativePosition.getX(),
                    current.getY() + relativePosition.getY());
            if (newJanggiPosition.equals(janggiPosition)) {
                return true;
            }
        }
        return false;
    }

}

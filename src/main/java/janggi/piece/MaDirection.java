package janggi.piece;

import janggi.value.JanggiPosition;
import java.util.List;

public enum MaDirection {
    LEFT(new JanggiPosition(-1, 0), List.of(new JanggiPosition(-2, -1), new JanggiPosition(-2, 1))),
    RIGHT(new JanggiPosition(1, 0), List.of(new JanggiPosition(2, -1), new JanggiPosition(2, 1))),
    UP(new JanggiPosition(0, -1), List.of(new JanggiPosition(-1, -2), new JanggiPosition(1, -2))),
    DOWN(new JanggiPosition(0, 1), List.of(new JanggiPosition(-1, 2), new JanggiPosition(1, 2))),
    NONE(new JanggiPosition(0, 0), List.of()),
    ;

    private final JanggiPosition route;
    private final List<JanggiPosition> destinationJanggiPositions;

    MaDirection(final JanggiPosition route, final List<JanggiPosition> destinationJanggiPositions) {
        this.route = route;
        this.destinationJanggiPositions = destinationJanggiPositions;
    }

    public static MaDirection of(final JanggiPosition current, final JanggiPosition destination) {
        int xDistance = destination.getX() - current.getX();
        int yDistance = destination.getY() - current.getY();

        for (MaDirection maDirection : MaDirection.values()) {
            List<JanggiPosition> destinationJanggiPositions = maDirection.destinationJanggiPositions;
            boolean isValidDirection = destinationJanggiPositions.stream()
                    .anyMatch(position -> position.equals(new JanggiPosition(xDistance, yDistance)));

            if (isValidDirection) {
                return maDirection;
            }
        }
        return NONE;
    }

    public boolean isDirectRoute(JanggiPosition current, JanggiPosition janggiPosition) {
        JanggiPosition newJanggiPosition = new JanggiPosition(current.getX() + route.getX(), current.getY() + route.getY());
        return newJanggiPosition.equals(janggiPosition);
    }

}

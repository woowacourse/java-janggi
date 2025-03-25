package janggi.piece.direction;

import janggi.value.JanggiPosition;
import janggi.value.RelativePosition;
import java.util.List;

public enum MaDirection {
    LEFT(new RelativePosition(-1, 0), List.of(new RelativePosition(-2, -1), new RelativePosition(-2, 1))),
    RIGHT(new RelativePosition(1, 0), List.of(new RelativePosition(2, -1), new RelativePosition(2, 1))),
    UP(new RelativePosition(0, -1), List.of(new RelativePosition(-1, -2), new RelativePosition(1, -2))),
    DOWN(new RelativePosition(0, 1), List.of(new RelativePosition(-1, 2), new RelativePosition(1, 2))),
    NONE(new RelativePosition(0, 0), List.of()),
    ;

    private final RelativePosition route;
    private final List<RelativePosition> destinationJanggiPositions;

    MaDirection(final RelativePosition route, final List<RelativePosition> destinationJanggiPositions) {
        this.route = route;
        this.destinationJanggiPositions = destinationJanggiPositions;
    }

    public static MaDirection of(final JanggiPosition current, final JanggiPosition destination) {
        int xDistance = destination.x() - current.x();
        int yDistance = destination.y() - current.y();

        for (MaDirection maDirection : MaDirection.values()) {
            boolean isValidDirection = maDirection.destinationJanggiPositions.stream()
                    .anyMatch(position -> position.equals(new RelativePosition(xDistance, yDistance)));

            if (isValidDirection) {
                return maDirection;
            }
        }
        return NONE;
    }

    public boolean isDirectRoute(JanggiPosition current, JanggiPosition janggiPosition) {
        JanggiPosition newJanggiPosition = new JanggiPosition(current.x() + route.getX(), current.y() + route.getY());
        return newJanggiPosition.equals(janggiPosition);
    }

}

package janggi.direction;

import janggi.value.Position;
import java.util.List;

public enum MaDirection {
    LEFT(new Position(-1, 0), List.of(new Position(-2, -1), new Position(-2, 1))),
    RIGHT(new Position(1, 0), List.of(new Position(2, -1), new Position(2, 1))),
    UP(new Position(0, -1), List.of(new Position(-1, -2), new Position(1, -2))),
    DOWN(new Position(0, 1), List.of(new Position(-1, 2), new Position(1, 2))),
    NONE(new Position(0, 0), List.of()),
    ;

    private final Position route;
    private final List<Position> destinationPositions;

    MaDirection(final Position route, final List<Position> destinationPositions) {
        this.route = route;
        this.destinationPositions = destinationPositions;
    }

    public static MaDirection of(final Position current, final Position destination) {
        int xDistance = destination.getX() - current.getX();
        int yDistance = destination.getY() - current.getY();

        for (MaDirection maDirection : MaDirection.values()) {
            List<Position> destinationPositions = maDirection.destinationPositions;
            boolean isValidDirection = destinationPositions.stream()
                    .anyMatch(position -> position.equals(new Position(xDistance, yDistance)));

            if (isValidDirection) {
                return maDirection;
            }
        }
        return NONE;
    }

    public boolean isDirectRoute(Position current, Position position) {
        Position newPosition = new Position(current.getX() + route.getX(), current.getY() + route.getY());
        return newPosition.equals(position);
    }

}

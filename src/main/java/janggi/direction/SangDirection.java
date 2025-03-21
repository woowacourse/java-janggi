package janggi.direction;

import janggi.value.Position;
import java.util.List;

public enum SangDirection {
    LEFT(List.of(new Position(-1, 0), new Position(-2, -1), new Position(-2, 1)),
            List.of(new Position(-3, -2), new Position(-3, 2))),
    RIGHT(List.of(new Position(1, 0), new Position(2, -1), new Position(2, 1)),
            List.of(new Position(3, -2), new Position(3, 2))),
    UP(List.of(new Position(0, -1), new Position(-1, -2), new Position(1, -2)),
            List.of(new Position(-2, -3), new Position(2, -3))),
    DOWN(List.of(new Position(0, 1), new Position(-1, 2), new Position(1, 2)),
            List.of(new Position(-2, 3), new Position(2, 3))),
    NONE(List.of(), List.of()),
    ;

    private final List<Position> routePositions;
    private final List<Position> destinationPositions;

    SangDirection(final List<Position> routePositions, final List<Position> destinationPositions) {
        this.routePositions = routePositions;
        this.destinationPositions = destinationPositions;
    }

    public static SangDirection of(final Position current, final Position destination) {
        int xDistance = destination.getX() - current.getX();
        int yDistance = destination.getY() - current.getY();

        for (SangDirection sangDirection : SangDirection.values()) {
            List<Position> destinationPositions = sangDirection.destinationPositions;
            boolean isValidDirection = destinationPositions.stream()
                    .anyMatch(position -> position.equals(new Position(xDistance, yDistance)));

            if (isValidDirection) {
                return sangDirection;
            }
        }
        return NONE;
    }

    public boolean isRoute(Position current, Position position) {
        for (Position routePosition : routePositions) {
            Position newPosition = new Position(current.getX() + routePosition.getX(),
                    current.getY() + routePosition.getY());
            if (newPosition.equals(position)) {
                return true;
            }
        }
        return false;
    }

}

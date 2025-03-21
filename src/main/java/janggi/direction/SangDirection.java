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
    NONE(List.of(), List.of());

    private final List<Position> positionsInPath;
    private final List<Position> destinationPositions;

    SangDirection(final List<Position> positionsInPath, final List<Position> destinationPositions) {
        this.positionsInPath = positionsInPath;
        this.destinationPositions = destinationPositions;
    }

    public static SangDirection of(final Position current, final Position destination) {
        Position relativePosition = destination.calculateDifference(current);
        List<SangDirection> allDirections = List.of(SangDirection.values());
        return allDirections.stream()
                .filter(direction -> direction.destinationPositions.contains(relativePosition))
                .findFirst()
                .orElse(NONE);
    }

    public boolean checkPositionInPath(Position current, Position target) {
        return positionsInPath.stream()
                .map(relativePosition -> current.calculateSum(relativePosition))
                .anyMatch(absolutePosition -> absolutePosition.equals(target));
    }
}

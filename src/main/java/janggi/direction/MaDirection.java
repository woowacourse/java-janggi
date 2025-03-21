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

    private final Position positionsInPath;
    private final List<Position> destinationPositions;

    MaDirection(final Position positionsInPath, final List<Position> destinationPositions) {
        this.positionsInPath = positionsInPath;
        this.destinationPositions = destinationPositions;
    }

    public static MaDirection of(final Position current, final Position destination) {
        Position relativePosition = destination.calculateDifference(current);
        List<MaDirection> allDirections = List.of(MaDirection.values());
        return allDirections.stream()
                .filter(direction -> direction.destinationPositions.contains(relativePosition))
                .findFirst()
                .orElse(NONE);
    }

    public boolean checkPositionInPath(Position current, Position target) {
        Position newPosition = new Position(current.x() + positionsInPath.x(),
                current.y() + positionsInPath.y());
        return newPosition.equals(target);
    }

}

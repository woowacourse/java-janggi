package janggi.domain.position;

import java.util.ArrayList;
import java.util.List;

public record Path(
        Position finalPosition,
        List<Position> pathPositions
) {
    public static Path start(final Position startPosition) {
        final List<Position> paths = new ArrayList<>();
        paths.add(startPosition);
        return new Path(startPosition, paths);
    }

    public Path nextPath(Movement movement) {
        List<Position> positions = new ArrayList<>(pathPositions);
        final List<Position> result = movement.getPositionsWith(finalPosition);
        positions.addAll(result);
        return new Path(positions.getLast(), positions);
    }

    public Path nextPath(Position position) {
        final List<Position> positions = finalPosition.createPositionsUntil(position);
        positions.addAll(pathPositions);
        return new Path(positions.getLast(), positions);
    }

    public boolean isBlockedWith(final List<Position> blockedPositions) {
        return pathPositions.subList(0, pathPositions.size() - 1).stream()
                .anyMatch(blockedPositions::contains);
    }

    public boolean isEndedWith(final List<Position> blockedPositions) {
        return blockedPositions.contains(finalPosition);
    }
}

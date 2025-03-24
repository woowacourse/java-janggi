package janggi.domain.position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public record Path(
        List<Position> pathPositions
) {
    public static Path start(final Position startPosition) {
        final List<Position> paths = new ArrayList<>();
        paths.add(startPosition);
        return new Path(paths);
    }

    public Path nextPath(Movement movement) {
        List<Position> positions = new ArrayList<>(pathPositions);
        final List<Position> result = movement.getPositionsWith(pathPositions.getLast());
        positions.addAll(result);
        return new Path(positions);
    }

    public Path nextPath(Position position) {
        final List<Position> positions = pathPositions.getLast().createPositionsUntil(position);
        positions.addAll(pathPositions);
        return new Path(positions);
    }

    public boolean isBlockedWith(final List<Position> blockedPositions) {
        return pathPositions.subList(0, pathPositions.size() - 1).stream()
                .anyMatch(blockedPositions::contains);
    }

    public boolean isEndedWith(final List<Position> blockedPositions) {
        return blockedPositions.contains(pathPositions.getLast());
    }

    public boolean isSuperPathOf(final Path path) {
        return new HashSet<>(this.pathPositions).containsAll(path.pathPositions);
    }
}

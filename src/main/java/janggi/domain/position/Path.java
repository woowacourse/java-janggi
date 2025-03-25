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
        final List<Position> result = new ArrayList<>(movement.getPositionsWith(finalPosition()));
        positions.addAll(result);
        return new Path(positions);
    }

    public Path nextPath(Position position) {
        final List<Position> positions = new ArrayList<>(finalPosition().createPositionsUntil(position));
        positions.addAll(pathPositions);
        return new Path(positions);
    }

    public boolean isBlockedWith(final List<Position> blockedPositions) {
        return pathPositions.subList(0, pathPositions.size() - 1).stream()
                .anyMatch(blockedPositions::contains);
    }

    public boolean isEndWith(final List<Position> positions) {
        return positions.contains(finalPosition());
    }

    public boolean isStartWith(final Position position) {
        return pathPositions.getFirst().equals(position);
    }

    public boolean isSuperPathOf(final Path path) {
        return new HashSet<>(this.pathPositions).containsAll(path.pathPositions);
    }

    public Position finalPosition() {
        return pathPositions.getLast();
    }

    public List<Path> subPaths() {
        final List<Path> subPaths = new ArrayList<>();
        for (int size = 2; size < pathPositions.size(); size++) {
            for (int start = 0; start + size < pathPositions.size(); size++) {
                subPaths.add(new Path(pathPositions.subList(start, start + size)));
                subPaths.add(new Path(pathPositions.subList(start, start + size).reversed()));
            }
        }
        return subPaths;
    }
}

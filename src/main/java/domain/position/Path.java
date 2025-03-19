package domain.position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Path {

    private final Position finalPosition;
    private final List<Position> pathPositions;

    public Path(final Position finalPosition, final List<Position> pathPositions) {
        this.finalPosition = finalPosition;
        this.pathPositions = pathPositions;
    }

    public static Path start(final Position startPosition) {
        final List<Position> paths = new ArrayList<>();
        paths.add(startPosition);
        return new Path(
                startPosition,
                paths
        );
    }

    public Position getFinalPosition() {
        return finalPosition;
    }

    public List<Path> nextPath(final List<Position> nextPositions) {
        return nextPositions.stream()
                .map(nextPosition -> {
                    final ArrayList<Position> paths = new ArrayList<>(pathPositions);
                    paths.add(nextPosition);
                    return new Path(nextPosition, paths);
                })
                .toList();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Path path)) return false;
        return Objects.equals(finalPosition, path.finalPosition) && Objects.equals(pathPositions, path.pathPositions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(finalPosition, pathPositions);
    }

    @Override
    public String toString() {
        return "Path{" +
                "finalPosition=" + finalPosition +
                ", pathPositions=" + pathPositions +
                '}';
    }
}

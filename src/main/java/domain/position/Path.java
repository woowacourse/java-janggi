package domain.position;

import domain.piece.Piece;

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
        List<Path> paths = new ArrayList<>();

        for (Position nextPosition : nextPositions) {
            List<Position> newPathPositions = new ArrayList<>();

            if (finalPosition.distance(nextPosition) >= 2) {
                newPathPositions.addAll(finalPosition.createPositionsUntil(nextPosition));
            }

            newPathPositions.addAll(pathPositions);
            newPathPositions.add(nextPosition);
            paths.add(new Path(nextPosition, newPathPositions));
        }

        return paths;
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

    public List<Piece> getEncounteredMiddlePieces(final List<Piece> pieces) {
        return pieces.stream()
                .filter(piece -> pathPositions.subList(0, pathPositions.size() - 1).contains(piece.getPosition()))
                .toList();
    }

    public boolean isEncounteredLast(final List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.getPosition().equals(finalPosition));
    }
}

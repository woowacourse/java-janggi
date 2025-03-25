package position;

import java.util.ArrayList;
import java.util.List;
import piece.MoveDirection;
import piece.Piece;

public record Path(
        Position finalPosition,
        List<Position> pathPositions
) {

    public static List<Path> getMovablePaths(final Position startPosition, final List<MoveDirection> moveDirections) {
        List<Path> paths = new ArrayList<>();
        paths.add(Path.start(startPosition));
        for (MoveDirection moveDirection : moveDirections) {
            for (Path path : paths) {
                List<Position> nextPositions = moveDirection.calculateNextPositionsFrom(path.finalPosition());
                paths = path.nextPath(nextPositions);
            }
        }
        return paths;
    }

    private static Path start(final Position startPosition) {
        final List<Position> paths = new ArrayList<>();
        paths.add(startPosition);
        return new Path(
                startPosition,
                paths
        );
    }

    private List<Path> nextPath(final List<Position> nextPositions) {
        return nextPositions.stream()
                .map(this::createNextPath)
                .toList();
    }

    private Path createNextPath(final Position nextPosition) {
        List<Position> newPathPositions = new ArrayList<>();
        insertPositionsInMiddle(newPathPositions, nextPosition);
        newPathPositions.addAll(pathPositions);
        newPathPositions.add(nextPosition);
        return new Path(nextPosition, newPathPositions);
    }

    private void insertPositionsInMiddle(final List<Position> newPathPositions, final Position nextPosition) {
        if (finalPosition.distance(nextPosition) >= 2) {
            newPathPositions.addAll(finalPosition.createPositionsUntil(nextPosition));
        }
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

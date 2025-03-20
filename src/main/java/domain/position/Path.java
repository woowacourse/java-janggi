package domain.position;

import domain.piece.MoveDirection;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public record Path(
        Position finalPosition,
        List<Position> pathPositions
) {
    public static Path start(final Position startPosition) {
        final List<Position> paths = new ArrayList<>();
        paths.add(startPosition);
        return new Path(
                startPosition,
                paths
        );
    }

    public static List<Path> getMoveablePaths(final Position startPosition, final List<MoveDirection> moveDirections) {
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

    public List<Path> nextPath(final List<Position> nextPositions) {
        List<Path> paths = new ArrayList<>();

        for (Position nextPosition : nextPositions) {
            List<Position> newPathPositions = new ArrayList<>();
            insertPositionsInMiddle(newPathPositions, nextPosition);
            newPathPositions.addAll(pathPositions);
            newPathPositions.add(nextPosition);
            paths.add(new Path(nextPosition, newPathPositions));
        }

        return paths;
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

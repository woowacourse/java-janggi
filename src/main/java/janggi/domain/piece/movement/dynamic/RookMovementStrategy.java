package janggi.domain.piece.movement.dynamic;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Pieces;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RookMovementStrategy implements DynamicMovementStrategy {

    @Override
    public boolean isLegalDestination(Side side, Position origin, Position destination) {
        if (origin.hasSameX(destination)) {
            return !origin.hasSameY(destination);
        }
        return origin.hasSameY(destination);
    }

    @Override
    public boolean isLegalPath(Pieces existingPieces, Side side, Position origin, Position destination) {
        Pieces onPathPieces = existingPieces.getPiecesOnPath(findPathsToDestination(origin, destination));

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.isEnemyOnDestination(side, destination);
        }
        return true;
    }

    private Set<Position> findPathsToDestination(Position origin, Position destination) {
        if (isVerticalMove(origin, destination)) {
            return findAllVerticalMovablePositions(origin, destination);
        }
        return findAllHorizontalMovablePositions(origin, destination);
    }

    private Set<Position> findAllVerticalMovablePositions(Position origin, Position destination) {
        int start = Math.min(origin.getY(), destination.getY()) + 1;
        int end = Math.max(origin.getY(), destination.getY());

        return IntStream.rangeClosed(start, end)
            .mapToObj(y -> new Position(origin.getX(), y))
            .collect(Collectors.toSet());
    }

    private Set<Position> findAllHorizontalMovablePositions(Position origin, Position destination) {
        int start = Math.min(origin.getX(), destination.getX()) + 1;
        int end = Math.max(origin.getX(), destination.getX());

        return IntStream.rangeClosed(start, end)
            .mapToObj(x -> new Position(x, origin.getY()))
            .collect(Collectors.toSet());
    }

    private boolean isVerticalMove(Position origin, Position destination) {
        return destination.hasSameX(origin);
    }
}

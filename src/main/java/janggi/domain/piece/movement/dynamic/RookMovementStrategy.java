package janggi.domain.piece.movement.dynamic;

import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.pieces.PiecesView;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RookMovementStrategy extends DynamicMovementStrategy {

    @Override
    public boolean isLegalDestination(Side side, Position origin, Position destination) {
        if (origin.hasSameX(destination)) {
            return !origin.hasSameY(destination);
        }
        return origin.hasSameY(destination);
    }

    @Override
    public boolean isLegalPath(PiecesView existingPieces, Side side, Position origin, Position destination) {
        PiecesView onPathPieces = existingPieces.getPiecesOnPath(findPathsToDestination(origin, destination));

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.hasOnlyOnePiece() && onPathPieces.isEnemyOnDestination(side, destination);
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
        int start = Math.min(origin.y(), destination.y()) + 1;
        int end = Math.max(origin.y(), destination.y());

        return IntStream.rangeClosed(start, end)
            .mapToObj(y -> new Position(origin.x(), y))
            .collect(Collectors.toSet());
    }

    private Set<Position> findAllHorizontalMovablePositions(Position origin, Position destination) {
        int start = Math.min(origin.x(), destination.x()) + 1;
        int end = Math.max(origin.x(), destination.x());

        return IntStream.rangeClosed(start, end)
            .mapToObj(x -> new Position(x, origin.y()))
            .collect(Collectors.toSet());
    }

    private boolean isVerticalMove(Position origin, Position destination) {
        return destination.hasSameX(origin);
    }
}

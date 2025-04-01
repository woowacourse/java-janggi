package janggi.domain.piece.movement.normal.dynamic;

import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.pieces.PiecesView;
import java.util.Set;

public class PawnMovementStrategy extends DynamicMovementStrategy {

    private static final int MOVABLE_DISTANCE = 1;

    @Override
    public boolean isLegalDestination(Side side, Position origin, Position destination) {
        if (!isVerticalMove(origin, destination)) {
            return origin.hasXDistance(destination, MOVABLE_DISTANCE) && origin.hasYDistance(destination, 0);
        }
        if (side == Side.HAN) {
            return origin.hasSameX(destination) && origin.plusY(MOVABLE_DISTANCE).equals(destination);
        }
        return origin.hasSameX(destination) && origin.minusY(MOVABLE_DISTANCE).equals(destination);
    }

    @Override
    public boolean isLegalPath(PiecesView existingPieces, Side side, Position origin, Position destination) {
        PiecesView onPathPieces = existingPieces.getPiecesOnPath(findPathsToDestination(side, origin, destination));

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.isEnemyOnDestination(side, destination);
        }
        return true;
    }

    private Set<Position> findPathsToDestination(Side side, Position origin, Position destination) {
        if (isVerticalMove(origin, destination)) {
            return findAllVerticalMovablePositions(side, origin);
        }
        return Set.of(destination);
    }

    private Set<Position> findAllVerticalMovablePositions(Side side, Position origin) {
        if (side == Side.HAN) {
            return Set.of(origin.plusY(MOVABLE_DISTANCE));
        }
        return Set.of(origin.minusY(MOVABLE_DISTANCE));
    }

    private boolean isVerticalMove(Position origin, Position destination) {
        return destination.hasSameX(origin);
    }
}

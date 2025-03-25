package janggi.domain.piece.movement.dynamic;

import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import java.util.Set;

public class PawnMovementStrategy extends DynamicMovementStrategy {

    private static final int MOVABLE_DISTANCE = 1;

    @Override
    public boolean isLegalDestination(Side side, Position origin, Position destination) {
        if (!isVerticalMove(origin, destination)) {
            return destination.getXDistance(origin) == MOVABLE_DISTANCE;
        }
        if (side == Side.HAN) {
            return destination.getY() == origin.getY() + MOVABLE_DISTANCE;
        }
        return destination.getY() == origin.getY() - MOVABLE_DISTANCE;
    }

    @Override
    public boolean isLegalPath(Pieces existingPieces, Side side, Position origin, Position destination) {
        Pieces onPathPieces = existingPieces.getPiecesOnPath(findPathsToDestination(side, origin, destination));

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
        int x = origin.getX();
        int y = origin.getY();
        if (side == Side.HAN) {
            return Set.of(new Position(x, y + MOVABLE_DISTANCE));
        }
        return Set.of(new Position(x, y - MOVABLE_DISTANCE));
    }

    private boolean isVerticalMove(Position origin, Position destination) {
        return destination.hasSameX(origin);
    }
}

package janggi.domain.piece.movement.fixed;

import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;

public abstract class FixedMovementStrategy implements MovementStrategy {

    @Override
    public final boolean isMoveable(Pieces map, Position origin, Side side, Position destination) {
        return isLegalDestination(origin, destination) && isPathClear(map, side, origin, destination);
    }

    private boolean isPathClear(Pieces map, Side side, Position origin, Position destination) {
        Pieces onPathPieces = getAllPiecesOnPath(map, origin, destination);

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.isEnemyOnDestination(side, destination);
        }
        return true;
    }

    protected abstract boolean isLegalDestination(Position origin, Position destination);

    protected abstract Pieces getAllPiecesOnPath(Pieces map, Position origin, Position destination);
}

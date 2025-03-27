package janggi.domain.piece.movement.fixed;

import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;

public abstract class FixedMovementStrategy implements MovementStrategy {

    @Override
    public final boolean isMoveable(PiecesView map, Position origin, Side side, Position destination) {
        return isLegalDestination(origin, destination) && isPathClear(map, side, origin, destination);
    }

    private boolean isPathClear(PiecesView map, Side side, Position origin, Position destination) {
        PiecesView onPathPieces = getAllPiecesOnPath(map, origin, destination);

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.hasOnlyOnePiece() && onPathPieces.isEnemyOnDestination(side, destination);
        }
        return true;
    }

    protected abstract boolean isLegalDestination(Position origin, Position destination);

    protected abstract PiecesView getAllPiecesOnPath(PiecesView map, Position origin, Position destination);
}

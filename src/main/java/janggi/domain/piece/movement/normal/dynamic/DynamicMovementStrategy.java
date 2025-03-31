package janggi.domain.piece.movement.normal.dynamic;

import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;

public abstract class DynamicMovementStrategy implements MovementStrategy {

    @Override
    public final boolean isMoveable(PiecesView map, Position origin, Side side, Position destination) {
        return isLegalDestination(side, origin, destination) && isLegalPath(map, side, origin, destination);
    }

    protected abstract boolean isLegalDestination(Side side, Position origin, Position destination);

    protected abstract boolean isLegalPath(PiecesView existingPieces, Side side, Position origin, Position destination);
}

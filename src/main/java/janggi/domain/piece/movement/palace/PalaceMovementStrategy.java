package janggi.domain.piece.movement.palace;

import janggi.domain.Palace;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;

public abstract class PalaceMovementStrategy implements MovementStrategy {

    protected final MovementStrategy defaultMovementStrategy;

    public PalaceMovementStrategy(MovementStrategy defaultMovementStrategy) {
        this.defaultMovementStrategy = defaultMovementStrategy;
    }

    @Override
    public final boolean isMoveable(PiecesView map, Position origin, Side side, Position destination) {
        if (Palace.isInPalace(origin)) {
            return defaultMovementStrategy.isMoveable(map, origin, side, destination) ||
                isMovableInPalace(map, origin, side, destination);

        }
        return defaultMovementStrategy.isMoveable(map, origin, side, destination);
    }

    protected abstract boolean isMovableInPalace(PiecesView map, Position origin, Side side, Position destination);
}

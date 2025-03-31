package janggi.domain.piece.movement.palace;

import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;
import janggi.domain.piece.pieces.PiecesView;

public class KnightPalaceMovementStrategy extends PalaceMovementStrategy {

    public KnightPalaceMovementStrategy(MovementStrategy defaultMovementStrategy) {
        super(defaultMovementStrategy);
    }

    @Override
    protected boolean isMovableInPalace(PiecesView map, Position origin, Side side, Position destination) {
        return defaultMovementStrategy.isMoveable(map, origin, side, destination);
    }
}

package janggi.domain.piece;

import janggi.domain.space.Palace;
import janggi.domain.space.Position;
import janggi.domain.Side;
import janggi.domain.move.MovementStrategy;

public class Guard extends Piece {
    public Guard(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public boolean isWithinBoundary(Position position) {
        return Palace.isInside(position, this.getSide());
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }
}

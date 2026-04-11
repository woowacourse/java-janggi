package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.move.MovementStrategy;

public class Horse extends Piece {
    public Horse(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }
}

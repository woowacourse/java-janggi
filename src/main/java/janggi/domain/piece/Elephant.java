package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.move.MovementStrategy;

public class Elephant extends Piece {
    public Elephant(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }
}

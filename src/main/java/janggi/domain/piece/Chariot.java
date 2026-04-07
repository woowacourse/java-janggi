package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.move.MovementStrategy;

public class Chariot extends Piece {
    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }
}

package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.move.MovementStrategy;

public class Soldier extends Piece {
    public Soldier(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}

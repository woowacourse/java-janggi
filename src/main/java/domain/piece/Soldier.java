package domain.piece;

import domain.Side;
import domain.strategy.MovementStrategy;

public class Soldier extends Piece {
    public Soldier(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}

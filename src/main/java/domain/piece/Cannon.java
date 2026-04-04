package domain.piece;

import domain.Side;
import domain.strategy.MovementStrategy;

public class Cannon extends Piece {
    public Cannon(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public boolean canBeBridge() {
        return false;
    }

    @Override
    public boolean canBeCapturedByJump() {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }
}

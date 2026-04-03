package domain.piece;

import domain.Side;
import domain.strategy.MovementStrategy;

public class General extends Piece {
    public General(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public boolean isVital() {
        return true;
    }

    @Override
    public String toString() {
        return "궁";
    }
}

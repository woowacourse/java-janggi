package domain.piece;

import domain.Side;
import domain.strategy.MovementStrategy;

public class Guard extends Piece {
    public Guard(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public String toString() {
        return "사";
    }
}

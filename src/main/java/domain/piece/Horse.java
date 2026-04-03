package domain.piece;

import domain.Side;
import domain.strategy.MovementStrategy;

public class Horse extends Piece {
    public Horse(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public String toString() {
        return "마";
    }
}

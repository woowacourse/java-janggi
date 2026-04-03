package domain.piece;

import domain.Side;
import domain.strategy.MovementStrategy;

public class Elephant extends Piece {
    public Elephant(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public String toString() {
        return "상";
    }
}

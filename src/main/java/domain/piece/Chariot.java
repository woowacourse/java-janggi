package domain.piece;

import domain.Side;
import domain.strategy.MovementStrategy;

public class Chariot extends Piece {
    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public String toString() {
        return "차";
    }
}

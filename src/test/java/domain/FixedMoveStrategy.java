package domain;

import domain.strategy.MoveStrategy;

public class FixedMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove() {
        return true;
    }
}

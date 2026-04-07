package janggi.domain.piece.strategy;

import janggi.domain.position.Direction;

public class ChoSoldierStrategy extends SoldierStrategy {
    private static final ChoSoldierStrategy INSTANCE = new ChoSoldierStrategy();

    private ChoSoldierStrategy() {
        super(Direction.UP);
    }

    public static ChoSoldierStrategy getInstance() {
        return INSTANCE;
    }
}

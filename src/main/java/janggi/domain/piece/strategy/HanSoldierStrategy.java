package janggi.domain.piece.strategy;

import janggi.domain.position.Direction;

public class HanSoldierStrategy extends SoldierStrategy {
    private static final HanSoldierStrategy INSTANCE = new HanSoldierStrategy();

    private HanSoldierStrategy() {
        super(Direction.DOWN);
    }

    public static HanSoldierStrategy getInstance() {
        return INSTANCE;
    }
}

package janggi.domain.piece.strategy;

import janggi.domain.Palace;

public class ChoPalaceStrategy extends PalaceStrategy {
    private static final ChoPalaceStrategy INSTANCE = new ChoPalaceStrategy();

    private ChoPalaceStrategy() {
        super(Palace.cho());
    }

    public static ChoPalaceStrategy getInstance() {
        return INSTANCE;
    }
}

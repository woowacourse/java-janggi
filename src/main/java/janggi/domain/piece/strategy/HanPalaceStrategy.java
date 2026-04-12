package janggi.domain.piece.strategy;

import janggi.domain.Palace;

public class HanPalaceStrategy extends PalaceStrategy {
    private static final HanPalaceStrategy INSTANCE = new HanPalaceStrategy();

    private HanPalaceStrategy() {
        super(Palace.han());
    }

    public static HanPalaceStrategy getInstance() {
        return INSTANCE;
    }
}

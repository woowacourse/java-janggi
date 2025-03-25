package janggi.domain.rule.block;

import janggi.domain.Placement;
import janggi.domain.Route;

public class BlockOnlyOnceStrategy implements BlockStrategy {

    private static final int BLOCK_COUNT = 1;

    @Override
    public void validateIsBlock(final Placement placement, final Route route) {
        if (route.countPieceInRoute(placement) != BLOCK_COUNT) {
            throw new IllegalArgumentException("이동 경로에 기물이 1개 존재해야 합니다.");
        }
    }
}

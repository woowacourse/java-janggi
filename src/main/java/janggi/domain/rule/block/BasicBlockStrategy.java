package janggi.domain.rule.block;

import janggi.domain.Placement;
import janggi.domain.Route;

public class BasicBlockStrategy implements BlockStrategy {

    private static final int BLOCK_COUNT = 0;

    @Override
    public void validateIsBlock(final Placement placement, final Route route) {
        if (route.countPieceInRoute(placement) > BLOCK_COUNT) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재합니다.");
        }
    }
}

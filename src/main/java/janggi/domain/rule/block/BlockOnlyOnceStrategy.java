package janggi.domain.rule.block;

import janggi.domain.Board;
import janggi.domain.Route;

public class BlockOnlyOnceStrategy implements BlockStrategy {

    private static final int BLOCK_COUNT = 1;

    @Override
    public void validateIsBlock(final Board board, final Route route) {
        if (route.countPieceInRoute(board) != BLOCK_COUNT) {
            throw new IllegalArgumentException("이동 경로에 기물이 1개 존재해야 합니다.");
        }
    }
}

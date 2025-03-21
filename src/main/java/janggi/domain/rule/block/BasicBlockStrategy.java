package janggi.domain.rule.block;

import janggi.domain.Board;
import janggi.domain.Position;

public class BasicBlockStrategy implements BlockStrategy {

    private static final int BLOCK_COUNT = 0;

    @Override
    public void validateIsBlock(final Board board, final Position departure, final Position destination) {
        if (countPieceInRoute(board, departure, destination) > BLOCK_COUNT) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재합니다.");
        }
    }


}

package janggi.domain.rule.block;

import janggi.domain.Board;
import janggi.domain.Position;

public class CannonBlockStrategy implements BlockStrategy {

    private static final int BLOCK_COUNT = 1;

    @Override
    public void validateIsBlock(final Board board, final Position departure, final Position destination) {
        if (countPieceInRoute(board, departure, destination) != BLOCK_COUNT) {
            throw new IllegalArgumentException("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
        }
    }
}

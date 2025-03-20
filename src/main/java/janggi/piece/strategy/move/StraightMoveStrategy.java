package janggi.piece.strategy.move;

import janggi.Board;
import janggi.Position;

public class StraightMoveStrategy implements MoveStrategy {

    @Override
    public void validate(final Board board, final Position departure, final Position destination) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);

        if (isNotStraightMovement(diffRow, diffColumn)) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private boolean isNotStraightMovement(final int diffRow, final int diffColumn) {
        // 대각선 이동 or 커브(마, 상) 이동
        return Math.min(Math.abs(diffRow), Math.abs(diffColumn)) != 0;
    }
}

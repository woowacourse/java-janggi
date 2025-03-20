package janggi.piece.strategy.move;

import janggi.Board;
import janggi.Position;

public class SingleMoveStrategy implements MoveStrategy {

    public static final int SINGLE_STEP = 1;

    @Override
    public void validate(final Board board, final Position departure, final Position destination) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);

        if (Math.abs(diffRow) + Math.abs(diffColumn) != SINGLE_STEP) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}

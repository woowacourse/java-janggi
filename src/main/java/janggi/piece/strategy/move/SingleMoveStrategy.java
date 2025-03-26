package janggi.piece.strategy.move;

import janggi.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;

public class SingleMoveStrategy implements MoveStrategy {

    public static final int SINGLE_STEP = 1;

    @Override
    public void validate(final Board board, final Position departure, final Position destination) {
        if (Distance.of(departure, destination).getTotal() == SINGLE_STEP) {
            return;
        }
        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
}

package janggi.piece.strategy.move;

import janggi.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;

public class StraightMoveStrategy implements MoveStrategy {

    @Override
    public void validate(final Board board, final Position departure, final Position destination) {
        if (Distance.of(departure, destination).isStraight()) {
            return;
        }
        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
}

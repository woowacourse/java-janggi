package janggi.piece;

import janggi.board.VisibleBoard;
import janggi.coordinate.Position;

public class Guard extends Piece{

    private static final int GUARD_DISTANCE = 1;

    public Guard(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        return now.calculateDistance(destination) == GUARD_DISTANCE;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}

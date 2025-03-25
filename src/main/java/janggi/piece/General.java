package janggi.piece;

import janggi.board.VisibleBoard;
import janggi.coordinate.Position;

public class General extends Piece{

    private static final int GENERAL_DISTANCE = 1;

    public General(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        return now.calculateDistance(destination) == GENERAL_DISTANCE;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}

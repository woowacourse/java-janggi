package janggi.piece;

import janggi.board.VisibleBoard;
import janggi.coordinate.Position;

public class Soldier extends Piece {

    private static final int SOLDIER_DISTANCE = 1;

    public Soldier(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        if (country == Country.HAN) {
            return now.calculateDistance(destination) == SOLDIER_DISTANCE && now.isXLessThan(destination);
        }
        return now.calculateDistance(destination) == SOLDIER_DISTANCE && now.isXGreaterThan(destination);
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}

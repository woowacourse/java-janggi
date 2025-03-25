package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;

public class Guard extends Piece{

    private static final int GUARD_DISTANCE = 1;

    public Guard(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final Board board) {
        return now.calculateDistance(destination) == GUARD_DISTANCE;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}

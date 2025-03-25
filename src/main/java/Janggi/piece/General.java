package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;

public class General extends Piece{

    private static final int GENERAL_DISTANCE = 1;

    public General(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final Board board) {
        return now.calculateDistance(destination) == GENERAL_DISTANCE;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}

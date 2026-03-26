package janggi.domain.piece;

import janggi.domain.Side;

public class Ma extends Piece {

    private static final String PIECE_NAME = "마";

    public Ma(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

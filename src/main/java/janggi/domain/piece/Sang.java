package janggi.domain.piece;

import janggi.domain.Side;

public class Sang extends Piece {

    private static final String PIECE_NAME = "상";

    public Sang(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

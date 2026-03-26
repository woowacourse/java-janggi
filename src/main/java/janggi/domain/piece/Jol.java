package janggi.domain.piece;

import janggi.domain.Side;

public class Jol extends Piece {

    private static final String PIECE_NAME = "졸";

    public Jol(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

package janggi.domain.piece;

import janggi.domain.Side;

public class Gung extends Piece {

    private static final String PIECE_NAME = "궁";

    public Gung(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

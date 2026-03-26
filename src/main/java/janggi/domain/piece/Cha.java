package janggi.domain.piece;

import janggi.domain.Side;

public class Cha extends Piece {

    private static final String PIECE_NAME = "차";

    public Cha(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

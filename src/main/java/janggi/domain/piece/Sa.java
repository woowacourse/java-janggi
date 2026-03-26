package janggi.domain.piece;

import janggi.domain.Side;

public class Sa extends Piece {

    private static final String PIECE_NAME = "사";

    public Sa(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

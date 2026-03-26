package janggi.domain.piece;

import janggi.domain.Side;

public class Jolbyeong extends Piece {

    private static final String PIECE_NAME = "졸병";
    private static final String CHO_PIECE_NAME = "졸";
    private static final String HAN_PIECE_NAME = "병";

    public Jolbyeong(Side side) {
        super(PIECE_NAME, side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String getName() {
        if (side.equals(Side.HAN)) {
            return HAN_PIECE_NAME;
        }
        return CHO_PIECE_NAME;
    }
}

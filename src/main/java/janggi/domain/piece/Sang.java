package janggi.domain.piece;

import janggi.domain.Side;

public class Sang extends Piece {
    public Sang(Side side) {
        super(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

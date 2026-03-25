package janggi.domain.piece;

import janggi.domain.Side;

public class Sa extends Piece {
    public Sa(Side side) {
        super(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

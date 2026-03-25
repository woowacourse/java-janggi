package janggi.domain.piece;

import janggi.domain.Side;

public class Ma extends Piece {
    public Ma(Side side) {
        super(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}

package janggi.domain.piece;

import janggi.domain.Side;

public class Po extends Piece {
    public Po(Side side) {
        super(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

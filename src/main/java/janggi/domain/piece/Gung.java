package janggi.domain.piece;

import janggi.domain.Side;

public class Gung extends Piece {
    public Gung(Side side) {
        super(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

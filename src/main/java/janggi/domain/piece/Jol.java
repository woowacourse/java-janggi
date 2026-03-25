package janggi.domain.piece;

import janggi.domain.Side;

public class Jol extends Piece {
    public Jol(Side side) {
        super(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

package janggi.domain.piece;

import janggi.domain.Side;

public abstract class Piece {
    protected final Side side;

    protected Piece(Side side) {
        this.side = side;
    }

    public abstract boolean isEmpty();

    public boolean isSameSide(Side side) {
        return this.side.equals(side);
    }
}

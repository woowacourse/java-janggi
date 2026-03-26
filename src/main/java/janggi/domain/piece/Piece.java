package janggi.domain.piece;

import janggi.domain.Side;

public abstract class Piece {
    protected final String name;
    protected final Side side;

    protected Piece(String name, Side side) {
        this.name = name;
        this.side = side;
    }

    public abstract boolean isEmpty();

    public boolean isSameSide(Piece piece) {
        return this.side.equals(piece.side);
    }

    public String getName() {
        return name;
    }
}

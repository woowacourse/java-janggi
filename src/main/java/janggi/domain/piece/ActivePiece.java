package janggi.domain.piece;

import janggi.domain.Side;

public abstract class ActivePiece implements Piece {

    protected final String name;
    protected final Side side;

    protected ActivePiece(String name, Side side) {
        this.name = name;
        this.side = side;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSameSide(Piece piece) {
        return piece.isSameSide(side);
    }

    @Override
    public boolean isSameSide(Side side) {
        return this.side.equals(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}

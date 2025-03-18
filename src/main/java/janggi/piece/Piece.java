package janggi.piece;

import janggi.Side;

public abstract class Piece {
    Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public Side getSide() {
        return side;
    }
}

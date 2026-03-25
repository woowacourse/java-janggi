package domain.piece;

import domain.Side;

public abstract class Piece {

    private final Side side;

    public Piece(Side side) {
        this.side = side;
    }

    public boolean isChu() {
        return side.isChu();
    }

    public boolean isHan() {
        return side.isHan();
    }
}

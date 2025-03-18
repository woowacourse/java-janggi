package janggi.piece;

import janggi.Side;

public class King implements Piece {

    private final Side side;

    public King(final Side side) {
        this.side = side;
    }

    @Override
    public String getSymbol() {
        return "G";
    }

    @Override
    public boolean isCho() {
        return side == Side.CHO;
    }

    @Override
    public boolean isHan() {
        return side == Side.HAN;
    }
}

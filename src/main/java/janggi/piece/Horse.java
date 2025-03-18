package janggi.piece;

import janggi.Side;

public class Horse implements Piece {

    private final Side side;

    public Horse(final Side side) {
        this.side = side;
    }

    @Override
    public String getSymbol() {
        return "M";
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

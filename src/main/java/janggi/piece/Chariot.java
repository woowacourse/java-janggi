package janggi.piece;

import janggi.Side;

public class Chariot implements Piece {

    private final Side side;

    public Chariot(final Side side) {
        this.side = side;
    }

    @Override
    public String getSymbol() {
        return "C";
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

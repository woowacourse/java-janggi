package janggi.piece;

import janggi.Side;

public class Cannon implements Piece {

    private final Side side;

    public Cannon(final Side side) {
        this.side = side;
    }

    @Override
    public String getSymbol() {
        return "P";
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

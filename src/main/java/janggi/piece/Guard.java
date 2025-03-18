package janggi.piece;

import janggi.Side;

public class Guard implements Piece {

    private final Side side;

    public Guard(final Side side) {
        this.side = side;
    }

    @Override
    public String getSymbol() {
        return "S";
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

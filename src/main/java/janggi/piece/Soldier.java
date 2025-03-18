package janggi.piece;

import janggi.Side;

public class Soldier implements Piece {

    private Side side;

    public Soldier(final Side side) {
        this.side = side;
    }

    @Override
    public String getSymbol() {
        return "J";
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

package janggi.piece;

import janggi.Side;

public class Elephant implements Piece {

    private final Side side;


    public Elephant(final Side side) {
        this.side = side;
    }

    @Override
    public String getSymbol() {
        return "E";
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

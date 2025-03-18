package janggi.piece;

public class Empty implements Piece {

    public Empty() {
    }

    @Override
    public String getSymbol() {
        return "·";
    }

    @Override
    public boolean isCho() {
        return false;
    }

    @Override
    public boolean isHan() {
        return false;
    }
}

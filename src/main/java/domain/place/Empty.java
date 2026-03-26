package domain.place;

import domain.place.piece.Side;

public class Empty implements Place {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isSameSide(Side side) {
        return false;
    }

    @Override
    public Side getSide() {
        return Side.EMPTY;
    }

    @Override
    public String getFormat() {
        return "  ";
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}

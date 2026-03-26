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
        throw new IllegalArgumentException("[EROOR] 빈칸은 진형이 없습니다.");
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

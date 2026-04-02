package janggi.domain.game;

public class Turn {

    private final Side currentSide;

    private Turn(Side currentSide) {
        this.currentSide = currentSide;
    }

    public static Turn init() {
        return new Turn(Side.CHO);
    }

    public Turn next() {
        if (this.currentSide == Side.CHO) {
            return new Turn(Side.HAN);
        }
        return new Turn(Side.CHO);
    }

    public boolean isTurnOf(Side side) {
        return this.currentSide == side;
    }
}

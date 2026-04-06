package janggi.domain.game;

public class Turn {

    private final Side currentSide;

    public Turn(Side currentSide) {
        this.currentSide = currentSide;
    }

    public static Turn init() {
        return new Turn(Side.CHO);
    }

    public Turn next() {
        return new Turn(currentSide.opposite());
    }

    public boolean isTurnOf(Side side) {
        return this.currentSide == side;
    }

    public Side currentSide() {
        return currentSide;
    }
}

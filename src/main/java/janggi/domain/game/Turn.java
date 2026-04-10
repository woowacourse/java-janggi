package janggi.domain.game;

public record Turn(Side currentSide) {

    public static Turn init() {
        return new Turn(Side.CHO);
    }

    public Turn next() {
        return new Turn(currentSide.opposite());
    }

    public boolean isTurnOf(Side side) {
        return this.currentSide == side;
    }
}

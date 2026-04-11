package janggi.domain.game;

public class Turn {
    private Side current;

    private Turn(Side side) {
        this.current = side;
    }

    public static Turn from(Side side) {
        return new Turn(side);
    }

    public void switchTurn() {
        this.current = current.opposite();
    }

    public boolean isCurrent(Side side) {
        return this.current == side;
    }

    public Side getSide() {
        return current;
    }
}

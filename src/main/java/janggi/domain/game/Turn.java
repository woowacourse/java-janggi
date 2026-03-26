package janggi.domain.game;

public class Turn {
    private Side current;

    public Turn() {
        this.current = Side.CHO;
    }

    public void switchTurn() {
        this.current = current.opposite();
    }

    public boolean isCurrent(Side side) {
        return this.current == side;
    }
}

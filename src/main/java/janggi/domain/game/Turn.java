package janggi.domain.game;

public class Turn {
    private Side current;

    public Turn(Side initiativeSide) {
        current = initiativeSide;
    }

    public void switchTurn() {
        current = current.opposite();
    }

    public boolean isCurrent(Side side) {
        return current == side;
    }
}

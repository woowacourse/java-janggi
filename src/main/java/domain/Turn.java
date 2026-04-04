package domain;

public class Turn {

    private Side current;

    public Turn(Side side) {
        this.current = side;
    }

    public Side current() {
        return current;
    }

    public void next() {
        current = current.opposite();
    }
}

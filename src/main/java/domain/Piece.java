package domain;

public abstract class Piece {
    private final Side side;

    public Piece(Side side) {
        this.side = side;
    }


    public boolean isSameSideAs(Side other) {
        return side.isSameAs(other);
    }
}

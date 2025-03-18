package janggi.domain.piece;

public class Piece {
    private final Side side;
    private final Movable movable;

    public Piece(Side side, Movable movable) {
        this.side = side;
        this.movable = movable;
    }
}

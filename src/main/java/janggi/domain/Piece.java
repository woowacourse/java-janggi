package janggi.domain;

public class Piece {

    private final Side side;
    private final Position position;

    public Piece(Side side, Position position) {
        this.side = side;
        this.position = position;
    }

    public Side getSide() {
        return side;
    }

    public Position getPosition() {
        return position;
    }
}

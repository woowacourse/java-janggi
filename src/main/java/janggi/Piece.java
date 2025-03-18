package janggi;

public class Piece {
    Side side;
    PieceType type;

    public Piece(Side side, PieceType type) {
        this.side = side;
        this.type = type;
    }

    public Side getSide() {
        return side;
    }

    public PieceType getType() {
        return type;
    }
}

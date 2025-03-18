package janggi.domain.piece;

public class Piece {

    private final Side side;
    private final PieceBehavior pieceBehavior;

    public Piece(Side side, PieceBehavior pieceBehavior) {
        this.side = side;
        this.pieceBehavior = pieceBehavior;
    }

    public String toName() {

        if (side == Side.CHO) {
            return "\u001B[32m" + pieceBehavior.toName() + "\u001B[0m";
        }
        return "\u001B[31m" + pieceBehavior.toName() + "\u001B[0m";
    }
}

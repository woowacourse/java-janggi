package piece;

public class Piece {

    private final PieceType pieceType;
    private final Side side;

    private Piece(PieceType pieceType, Side side) {
        this.pieceType = pieceType;
        this.side = side;
    }

    public static Piece of(Side side, PieceType pieceType) {
        return new Piece(pieceType, side);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Side getSide() {
        return side;
    }
}

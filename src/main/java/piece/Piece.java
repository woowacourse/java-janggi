package piece;

public abstract class Piece {
    private final PieceType pieceType;
    private final Country country;


    protected Piece(final PieceType pieceType, final Country country) {
        this.pieceType = pieceType;
        this.country = country;
    }
}

package janggi.domain.piece;

public class EmptyPiece extends Piece{
    public static final Piece INSTANCE = new EmptyPiece();

    public EmptyPiece() {
        super(TeamColor.NONE, PieceType.NONE);
    }
}

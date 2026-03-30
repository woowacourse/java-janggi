package pieces;

public interface Piece {

    boolean isEmpty();

    PieceType type();

    FullPiece asFullPiece();
}

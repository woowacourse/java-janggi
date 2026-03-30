package pieces;

public final class EmptyPiece implements Piece {

    private static final EmptyPiece EMPTY_PIECE = new EmptyPiece();

    private EmptyPiece() {
    }

    public static EmptyPiece getInstance() {
        return EMPTY_PIECE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public PieceType type() {
        return PieceType.EMPTY;
    }

    @Override
    public FullPiece asFullPiece() {
        throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
    }
}

package pieces;

public final class EmptyPiece implements Piece {

    private static EmptyPiece EMPTY_PIECE;

    private EmptyPiece() {
    }

    public static EmptyPiece getInstance() {
        if (EMPTY_PIECE == null) {
            EMPTY_PIECE = new EmptyPiece();
            return EMPTY_PIECE;
        }
        return EMPTY_PIECE;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public FullPiece asFullPiece() {
        throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
    }
}

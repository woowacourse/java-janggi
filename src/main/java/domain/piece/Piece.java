package domain.piece;

public class Piece {
    private final PieceInfo pieceInfo;

    public Piece(PieceInfo pieceInfo) {
        this.pieceInfo = pieceInfo;
    }

    public PieceInfo getPieceInfo() {
        return pieceInfo;
    }
}

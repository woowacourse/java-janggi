package domain;

public class Piece {
    private final Camp camp;
    private final PieceType pieceType;

    public Piece(Camp camp, PieceType pieceType) {
        this.camp = camp;
        this.pieceType = pieceType;
    }

    public Camp camp() {
        return this.camp;
    }
}

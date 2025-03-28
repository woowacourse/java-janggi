package domain.piece;

public enum PieceType {
    GENERAL(0),
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    GUARD(3),
    SOLDIER(2),
    EMPTY(0),
    ;

    private final double pieceScore;

    PieceType(double pieceScore) {
        this.pieceScore = pieceScore;
    }

    public double getPieceScore() {
        return pieceScore;
    }
}

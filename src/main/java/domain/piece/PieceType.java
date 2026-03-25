package domain.piece;

public enum PieceType {
    CHA(13.0),
    MA(5.0),
    SANG(3.0),
    SA(3.0),
    GENERAL(0.0),
    PHO(7.0),
    BYEONG(2.0),
    EMPTY(0.0);

    private final double score;

    PieceType(double score) {
        this.score = score;
    }
}

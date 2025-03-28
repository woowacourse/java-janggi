package janggi.piece;

public enum PieceType {

    GUNG(0),
    SA(3),
    MA(5),
    SANG(3),
    CHA(13),
    PO(7),
    JOL(2),
    BYEONG(2),
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

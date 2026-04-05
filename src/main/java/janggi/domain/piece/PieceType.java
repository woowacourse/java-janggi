package janggi.domain.piece;

public enum PieceType {
    CHA(13),
    PHO(7),
    MA(5),
    SANG(3),
    JANG(0),
    SA(3),
    JOL(2),
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

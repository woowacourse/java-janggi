package janggi.domain.piece;

public enum PieceType {
    CHA("차", 13),
    GUNG("궁", 0),
    JOL("졸", 2),
    MA("마", 5),
    PO("포", 7),
    SA("사", 3),
    SANG("상", 3),
    ;

    PieceType(String name, int score) {
        this.name = name;
        this.score = score;
    }

    private final String name;
    private final int score;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

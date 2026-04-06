package janggi.domain.piece;

public enum PieceType {
    CHA("차", 13),
    GUNG("궁", 0),
    JOLBYEOUNG("졸,병", 2),
    MA("마", 5),
    PO("포", 7),
    SA("사", 3),
    SANG("상", 3),
    EMPTY("없음", 0);

    private final String displayName;
    private final double score;

    PieceType(String displayName, double score) {
        this.displayName = displayName;
        this.score = score;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public double getScore() {
        return score;
    }
}

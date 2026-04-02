package janggi.domain.piece;

public enum PieceType {
    CHA("차", 3),
    PHO("포", 3),
    MA("마", 3),
    SANG("상", 3),
    JANG("장", 5),
    SA("사", 1),
    JOL("졸", 1);

    private final String name;
    private final int score;

    PieceType(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

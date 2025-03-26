package domain;

public enum PieceType {
    MA("마", 5),
    CHA("차", 13),
    SANG("상", 3),
    SA("사", 3),
    GUNG("궁", 0),
    PHO("포", 7),
    BYEONG("병", 2),
    ;

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

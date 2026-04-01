package janggi.domain;

public enum PieceType {
    CHA("車", 13),
    PO("包", 7),
    MA("馬", 5),
    SANG("象", 3),
    SA("士", 3),
    HAN_GUNG("將", 0),
    CHO_GUNG("楚", 0),
    HAN_JOL("兵", 2),
    CHO_JOL("卒", 2);

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

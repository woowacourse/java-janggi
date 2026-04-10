package janggi.domain.piece;

public enum PieceType {
    CHA("CH", 13),
    GUNG("GU", 0),
    MA("MA", 5),
    NONE(".", 0),
    PAWN("JO", 2),
    PO("PO", 7),
    SA("SA", 3),
    SANG("SG", 3);

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

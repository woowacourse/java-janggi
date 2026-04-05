package domain.piece;

public enum PieceType {
    BLANK(".", 0),
    CANNON("C", 7),
    CHARIOT("R", 13),
    ELEPHANT("M", 3),
    GUARD("G", 3),
    HORSE("N", 5),
    KING("K", 0),
    PAWN("P", 2);


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

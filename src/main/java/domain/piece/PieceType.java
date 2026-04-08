package domain.piece;

public enum PieceType {
    GENERAL("궁", 0),
    GUARD("사", 3),
    CHARIOT("차", 13),
    CANNON("포", 7),
    ELEPHANT("상", 3),
    HORSE("마", 5),
    SOLDIER("졸", 2);

    private final String displayName;
    private final int score;

    PieceType(String displayName, int score) {
        this.displayName = displayName;
        this.score = score;
    }

    public String display() {
        return displayName;
    }

    public int score() {
        return score;
    }
}

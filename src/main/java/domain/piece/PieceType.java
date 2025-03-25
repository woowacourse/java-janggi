package domain.piece;

public enum PieceType {

    CANNON("C", 7),
    CHARIOT("R", 13),
    ELEPHANT("E", 3),
    GUARD("G", 3),
    HORSE("H", 5),
    KING("K", 0),
    SOLDIER("S", 2);

    private final String description;
    private final int score;

    PieceType(String description, int score) {
        this.description = description;
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public int getScore() {
        return score;
    }
}

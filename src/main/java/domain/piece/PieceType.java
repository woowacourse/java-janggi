package domain.piece;

public enum PieceType {

    CANNON("C", 7),
    CHARIOT("c", 13),
    ELEPHANT("E", 3),
    GENERAL("G", 0),
    GUARD("g", 3),
    HORSE("H", 5),
    SOLDIER("s", 2),
    ;

    private final String name;
    private final int score;

    PieceType(final String name, final int score) {
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

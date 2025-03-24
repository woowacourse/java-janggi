package janggi.piece;

public enum PieceName {
    KING("K", 0),
    GUARD("G", 3),
    HORSE("H", 5),
    ELEPHANT("E", 3),
    CHARIOT("C", 13),
    CANNON("P", 7),
    SOLDIER("S", 2);

    private final String name;
    private final int score;

    PieceName(String name, int score) {
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

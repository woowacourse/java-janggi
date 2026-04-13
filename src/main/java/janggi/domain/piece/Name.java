package janggi.domain.piece;

public enum Name {
    GENERAL("將", 0),
    CHARIOT("車", 13),
    CANNON("包", 7),
    HORSE("馬", 5),
    ELEPHANT("象", 3),
    GUARD("士", 3),
    SOLDIER("兵", 2);

    private final String name;
    private final int score;

    Name(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int score() {
        return score;
    }
}

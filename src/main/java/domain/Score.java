package domain;

public enum Score {
    GENERAL(0),
    SOLDIER(2),
    GUARD(3),
    ELEPHANT(4),
    HORSE(5),
    CANNON(7),
    CHARIOT(13);

    private final int score;

    Score(final int score) {
        this.score = score;
    }
}

package domain;

public enum PieceType {
    GENERAL(0),
    GUARD(3),
    HORSE(5),
    CANNON(7),
    ELEPHANT(3),
    SOLDIER(2),
    CHARIOT(13);

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

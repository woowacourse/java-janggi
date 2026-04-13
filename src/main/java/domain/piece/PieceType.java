package domain.piece;

public enum PieceType {
    GENERAL(0),
    GUARD(3),
    CHARIOT(13),
    CANNON(7),
    ELEPHANT(3),
    HORSE(5),
    SOLDIER(2);

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int score() {
        return score;
    }
}

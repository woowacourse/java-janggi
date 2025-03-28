package domain.piece;

public enum PieceType {
    CANNON(7),
    CHARIOT(13),
    ELEPHANT(3),
    GUARD(3),
    HORSE(5),
    KING(0),
    SOLDIER(2);

    private final double score;

    PieceType(int score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}

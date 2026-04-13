package domain.piece;

public enum PieceType {

    GENERAL(0),
    GUARD(3),
    HORSE(5),
    ELEPHANT(5),
    SOLDIER(2),
    CANNON(7),
    CHARIOT(13);

    private final double score;

    PieceType(final double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}

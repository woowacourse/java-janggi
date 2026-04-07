package domain.piece;

public enum PieceType {
    SOLDIER(2d),
    GUARD(3d),
    ELEPHANT(3d),
    HORSE(5d),
    CANNON(7d),
    CHARIOT(13d),
    GENERAL(0d),
    ;

    private final double score;

    PieceType(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}

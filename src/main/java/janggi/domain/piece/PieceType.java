package janggi.domain.piece;

public enum PieceType {

    GENERAL(0.0),
    CHARIOT(13.0),
    CANNON(7.0),
    HORSE(5.0),
    ELEPHANT(3.0),
    GUARD(3.0),
    SOLDIER(2.0);

    private final double score;

    PieceType(double score) {
        this.score = score;
    }

    public double score() {
        return score;
    }

    public boolean isGeneral() {
        return this == GENERAL;
    }
}

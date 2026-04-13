package model.piece;

public enum PieceType {
    GENERAL(0),
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    GUARD(3),
    SOLDIER(2);

    private final double score;

    PieceType(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}

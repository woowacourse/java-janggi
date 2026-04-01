package model.piece;

public enum PieceType {

    CANNON(7), CHARIOT(13), ELEPHANT(3), GENERAL(0), GUARD(2), HORSE(5), SOLDIER(3);

    private final double score;

    PieceType(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}

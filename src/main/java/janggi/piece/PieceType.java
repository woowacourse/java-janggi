package janggi.piece;

public enum PieceType {
    KING(0),
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    SOLDIER(3),
    HANPAWN(2),
    CHOPAWN(2),
    BLANK(0);

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public double score() {
        return score;
    }
}

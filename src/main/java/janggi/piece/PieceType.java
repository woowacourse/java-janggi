package janggi.piece;

public enum PieceType {
    TANK(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    GUARD(3),
    SOLDIER(2),
    KING(0);

    private final int score;

    PieceType(final int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

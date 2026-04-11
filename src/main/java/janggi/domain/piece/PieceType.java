package janggi.domain.piece;

public enum PieceType {
    CHARIOT(13),
    HORSE(5),
    ELEPHANT(3),
    GUARD(3),
    GENERAL(0),
    CANNON(7),
    SOLDIER(2),
    EMPTY(0);

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

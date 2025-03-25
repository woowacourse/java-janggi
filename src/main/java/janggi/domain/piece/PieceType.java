package janggi.domain.piece;

public enum PieceType {
    SOLDIER(2),
    HORSE(5),
    CHARIOT(13),
    ELEPHANT(3),
    CANNON(7),
    GENERAL(0),
    GUARD(3),
    NONE(0)
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

package janggi.domain.piece;

public enum PieceType {
    GENERAL(0),
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    GUARD(3),
    ELEPHANT(3),
    SOLIDER(2)
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

package janggi.domain.piece;

public enum PieceType {
    GENERAL(0),
    CANNON(7),
    CHARIOT(13),
    ELEPHANT(3),
    GUARD(3),
    HORSE(5),
    SOLIDER(2);

    private final int score;

    PieceType(int score) {
        this.score = score;
    }
}

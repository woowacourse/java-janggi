package janggi.piece;

public enum PieceType {
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    SCHOLAR(3),
    SOLDIER(2),
    KING(0),
    NONE(0),
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }
}

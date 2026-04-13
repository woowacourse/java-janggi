package domain.piece;

public enum PieceType {
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    GUARD(3),
    GENERAL(0),
    SOLDIER(2),
    EMPTY(0),
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int score() {
        return score;
    }
}

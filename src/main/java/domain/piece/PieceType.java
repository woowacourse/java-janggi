package domain.piece;

public enum PieceType {
    SOLDIER(2),
    GUARD(3),
    ELEPHANT(3),
    HORSE(5),
    CANNON(7),
    CHARIOT(13),
    GENERAL(0),
    EMPTY(0),
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

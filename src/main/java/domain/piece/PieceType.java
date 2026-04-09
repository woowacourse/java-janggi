package domain.piece;

public enum PieceType {
    GENERAL(0),
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    GUARD(3),
    SOLDIER(2),
    NONE(0),
    ;

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

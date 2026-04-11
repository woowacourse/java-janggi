package team.janggi.domain.piece;

public enum PieceType {
    KING(0),
    GUARD(3),
    HORSE(5),
    ELEPHANT(3),
    CHARIOT(13),
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

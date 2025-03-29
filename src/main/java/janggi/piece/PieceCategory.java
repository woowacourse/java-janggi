package janggi.piece;

public enum PieceCategory {

    GENERAL(0),
    CHARIOT(13),
    CANNON(7),
    HORSE(5),
    ELEPHANT(3),
    GUARD(3),
    SOLDIER(2);

    private final int score;

    PieceCategory(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

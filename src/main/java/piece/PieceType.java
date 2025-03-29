package piece;

public enum PieceType {
    CANNON(7),
    CHARIOT(13),
    ELEPHANT(3),
    GENERAL(0),
    SOLIDER(2),
    GUARD(3),
    HORSE(5);

    private final int score;

    PieceType(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public static boolean isCannon(Piece piece) {
        return piece.getPieceType() == CANNON;
    }

    public static boolean isNotCannon(Piece piece) {
        return piece.getPieceType() != CANNON;
    }

    public static boolean isGeneral(Piece piece) {
        return piece.getPieceType() == GENERAL;
    }
}

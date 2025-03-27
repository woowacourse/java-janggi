package piece;

public enum PieceType {
    CANNON,
    CHARIOT,
    ELEPHANT,
    GENERAL,
    SOLIDER,
    GUARD,
    HORSE;

    public static boolean isCannon(Piece piece) {
        return piece.getPieceType() == CANNON;
    }

    public static boolean isNotCannon(Piece piece) {
        return piece.getPieceType() != CANNON;
    }
}

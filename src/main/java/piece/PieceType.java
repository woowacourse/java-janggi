package piece;

public enum PieceType {
    GENERAL("장", 0),
    GUARD("사", 3),
    ELEPHANT("상", 3),
    HORSE("마", 5),
    ROOK("차", 13),
    SOLDIER("병", 2),
    CANNON("포", 7);

    private final String displayName;
    private final int pieceScore;

    PieceType(final String displayName, final int pieceScore) {
        this.displayName = displayName;
        this.pieceScore = pieceScore;
    }

    public static PieceType of(final String pieceType) {
        return valueOf(pieceType);
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPieceScore() {
        return pieceScore;
    }
}

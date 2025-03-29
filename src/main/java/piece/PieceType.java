package piece;

public enum PieceType {
    GENERAL("장"),
    GUARD("사"),
    ELEPHANT("상"),
    HORSE("마"),
    ROOK("차"),
    SOLDIER("병"),
    CANNON("차");

    private final String displayName;

    PieceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

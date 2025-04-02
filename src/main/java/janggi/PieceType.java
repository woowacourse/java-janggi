package janggi;

public enum PieceType {
    GENERAL("궁"),
    GUARD("사"),
    HORSE("마"),
    ELEPHANT("상"),
    CANON("포"),
    CHARIOT("차"),
    SOLDIER("졸");

    private final String displayName;

    PieceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

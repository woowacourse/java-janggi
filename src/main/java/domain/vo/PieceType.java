package domain.vo;

public enum PieceType {
    GENERAL("궁"),
    GUARD("사"),
    CHARIOT("차"),
    CANNON("포"),
    ELEPHANT("상"),
    HORSE("마"),
    SOLDIER("졸");

    private final String displayName;

    PieceType(String displayName) {
        this.displayName = displayName;
    }

    public String display() {
        return displayName;
    }
}

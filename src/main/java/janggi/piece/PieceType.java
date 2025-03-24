package janggi.piece;

public enum PieceType {
    GENERAL("장"),
    GUARD("사"),
    SOLDIER("병/졸"),
    HORSE("마"),
    ELEPHANT("상"),
    CHARIOT("차"),
    CANNON("포"),
    ;

    private final String description;

    PieceType(final String description) {
        this.description = description;
    }

    public boolean isCannon() {
        return this == CANNON;
    }

    public String getDescription() {
        return description;
    }
}

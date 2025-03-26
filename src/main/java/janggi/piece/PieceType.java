package janggi.piece;

public enum PieceType {
    CANNON("포"),
    CHARIOT("차"),
    ELEPHANT("상"),
    GENERAL("장"),
    GUARD("사"),
    HORSE("마"),
    SOLDIER("병");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

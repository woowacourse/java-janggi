package janggi.model;

public enum PieceType {
    CANNON("포"),
    CHARIOT("차"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    SOLDIER("졸"),
    KING("궁"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

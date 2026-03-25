package domain;

public enum PieceType {

    GENERAL("궁"),
    GUARD("사"),
    SOLDIER("졸"),

    HORSE("마"),
    ELEPHANT("상"),
    CHARIOT("차"),

    CANNON("포"),

    EMPTY_VALUE("*")
    ;

    private final String description;

    PieceType(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }
}

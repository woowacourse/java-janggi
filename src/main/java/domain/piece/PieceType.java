package domain.piece;

public enum PieceType {
    GENERAL("궁"),
    CHARIOT("차"),
    CANNON("포"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    SOLDIER("졸"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

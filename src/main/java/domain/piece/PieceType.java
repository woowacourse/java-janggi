package domain.piece;

public enum PieceType {
    CANNON("포"),
    CHARIOT("차"),
    ELEPHANT("상"),
    GENERAL("궁"),
    GUARD("사"),
    HORSE("마"),
    SOLDIER("졸"),
    EMPTY("ㅁ"),
    ;

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

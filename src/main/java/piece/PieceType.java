package piece;

public enum PieceType {

    KING("궁"),
    CANNON("포"),
    CHARIOT("차"),
    HORSE("마"),
    ELEPHANT("상"),
    GUARD("사"),
    SOLDIER("졸"),
    ;

    private final String name;

    PieceType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

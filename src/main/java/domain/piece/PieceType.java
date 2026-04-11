package domain.piece;

public enum PieceType {
    //TODO: 출력 책임 분리
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

    public boolean isCannon() {
        return this == CANNON;
    }
}

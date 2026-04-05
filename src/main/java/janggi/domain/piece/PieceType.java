package janggi.domain.piece;

public enum PieceType {
    GENERAL("장"),
    CHARIOT("차"),
    CANNON("포"),
    ELEPHANT("상"),
    HORSE("마"),
    GUARD("사"),
    SOLDIER("졸"),
    EMPTY("빈");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

package janggi.domain.piece;

public enum PieceType {
    SOLDIER("졸"),
    ADVISOR("사"),
    CANNON("포"),
    ELEPHANT("상"),
    HORSE("마"),
    KING("장"),
    TANK("차"),
    EMPTY("X");

    private final String name;

    PieceType(String name) {
        this.name = name;
    }
}

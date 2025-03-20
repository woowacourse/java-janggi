package janggi.piece;

public enum PieceType {
    GENERAL,
    GUARD,
    SOLDIER,
    HORSE,
    ELEPHANT,
    CHARIOT,
    CANNON,
    ;

    public boolean isCannon() {
        return this == CANNON;
    }
}

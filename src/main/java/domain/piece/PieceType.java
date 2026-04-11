package domain.piece;

public enum PieceType {
    GENERAL,
    GUARD,
    CHARIOT,
    CANNON,
    ELEPHANT,
    HORSE,
    SOLDIER;

    public boolean isCannon() {
        return this == CANNON;
    }
}

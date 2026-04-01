package domain;

public enum PieceType {
    GENERAL,
    GUARD,
    CHARIOT,
    CANNON,
    HORSE,
    ELEPHANT,
    SOLDIER;

    private MoveStrategy moveStrategy;

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }
}

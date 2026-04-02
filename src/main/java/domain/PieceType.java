package domain;

public enum PieceType {
    GENERAL(new ChariotMoveStrategy()),
    GUARD(new ChariotMoveStrategy()),
    CHARIOT(new ChariotMoveStrategy()),
    CANNON(new ChariotMoveStrategy()),
    HORSE(new ChariotMoveStrategy()),
    ELEPHANT(new ChariotMoveStrategy()),
    SOLDIER(new ChariotMoveStrategy());

    private MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }
}

package domain;

public enum PieceType {
    CHARIOT(new ChariotMoveStrategy()),
    CANNON(new CannonMoveStrategy());
    private final MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public boolean isMovable(BoardLocation current, BoardLocation target) {
        return this.moveStrategy.isMovable(current, target);
    }
}

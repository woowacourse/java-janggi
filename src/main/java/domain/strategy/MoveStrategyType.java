package domain.strategy;

public enum MoveStrategyType {
    SOLDIER(new SoldierMoveStrategy()),
    GUARD(new InsidePalaceMoveStrategy()),
    ELEPHANT(new ElephantMoveStrategy()),
    HORSE(new HorseMoveStrategy()),
    CANNON(new CannonMoveStrategy()),
    CHARIOT(new StraightMoveStrategy()),
    GENERAL(new InsidePalaceMoveStrategy());

    private final MoveStrategy moveStrategy;

    MoveStrategyType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}

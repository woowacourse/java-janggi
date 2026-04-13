package domain.movestrategy;

public enum MoveStrategyType {
    GENERAL(new GeneralMoveStrategy()),
    GUARD(new GuardMoveStrategy()),
    HORSE(new HorseMoveStrategy()),
    ELEPHANT(new ElephantMoveStrategy()),
    SOLDIER(new SoldierMoveStrategy()),
    CANNON(new CannonMoveStrategy()),
    CHARIOT(new ChariotMoveStrategy());

    private final MoveStrategy moveStrategy;

    MoveStrategyType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}

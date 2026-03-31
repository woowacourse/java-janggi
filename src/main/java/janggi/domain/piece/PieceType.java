package janggi.domain.piece;

public enum PieceType {

    CANNON(CannonMoveStrategy.getInstance()),
    CHARIOT(ChariotMoveStrategy.getInstance()),
    ELEPHANT(ElephantMoveStrategy.getInstance()),
    GENERAL(GeneralMoveStrategy.getInstance()),
    GUARD(GeneralMoveStrategy.getInstance()),
    HORSE(HorseMoveStrategy.getInstance()),
    SOLDIER(SoldierMoveStrategy.getInstance());

    private final MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }
}

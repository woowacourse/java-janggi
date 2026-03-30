package janggi.domain.piece;

public enum PieceType {

    CANNON(CannonMoveStrategy.instance()),
    CHARIOT(ChariotMoveStrategy.instance()),
    ELEPHANT(ElephantMoveStrategy.instance()),
    GENERAL(GeneralMoveStrategy.instance()),
    GUARD(GuardMoveStrategy.instance()),
    HORSE(HorseMoveStrategy.instance()),
    SOLDIER(SoldierMoveStrategy.instance());

    private final MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }

}

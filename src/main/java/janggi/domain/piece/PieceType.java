package janggi.domain.piece;

public enum PieceType {

    CHARIOT(ChariotMoveStrategy.getInstance(), 13),
    CANNON(CannonMoveStrategy.getInstance(), 7),
    HORSE(HorseMoveStrategy.getInstance(), 5),
    ELEPHANT(ElephantMoveStrategy.getInstance(), 3),
    GUARD(GuardMoveStrategy.getInstance(), 3),
    SOLDIER(SoldierMoveStrategy.getInstance(), 2),
    GENERAL(GeneralMoveStrategy.getInstance(), 0);

    private final MoveStrategy moveStrategy;
    private final int score;

    PieceType(MoveStrategy moveStrategy, int score) {
        this.moveStrategy = moveStrategy;
        this.score = score;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }

    public int score() {
        return score;
    }
}

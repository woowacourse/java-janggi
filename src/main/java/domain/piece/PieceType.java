package domain.piece;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.InsidePalaceMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.OneStepMoveStrategy;
import domain.strategy.StraightMoveStrategy;

public enum PieceType {
    SOLDIER(2d, new OneStepMoveStrategy()),
    GUARD(3d, new InsidePalaceMoveStrategy()),
    ELEPHANT(3d, new ElephantMoveStrategy()),
    HORSE(5d, new HorseMoveStrategy()),
    CANNON(7d, new CannonMoveStrategy()),
    CHARIOT(13d, new StraightMoveStrategy()),
    GENERAL(0d, new InsidePalaceMoveStrategy()),
    ;

    private final double score;
    private final MoveStrategy moveStrategy;

    PieceType(double score, MoveStrategy moveStrategy) {
        this.score = score;
        this.moveStrategy = moveStrategy;
    }

    public double getScore() {
        return score;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}

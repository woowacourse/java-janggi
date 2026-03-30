package domain;

import strategy.move.CannonMoveStrategy;
import strategy.move.ElephantMoveStrategy;
import strategy.move.GuardMoveStrategy;
import strategy.move.HorseMoveStrategy;
import strategy.move.KingMoveStrategy;
import strategy.move.MoveStrategy;
import strategy.move.PawnMoveStrategy;
import strategy.move.RookMoveStrategy;

public enum PieceType {
    CANNON(new CannonMoveStrategy()),
    ELEPHANT(new ElephantMoveStrategy()),
    GUARD(new GuardMoveStrategy()),
    HORSE(new HorseMoveStrategy()),
    KING(new KingMoveStrategy()),
    PAWN(new PawnMoveStrategy()),
    ROOK(new RookMoveStrategy());

    private final MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }
}

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
    CANNON(new CannonMoveStrategy(), "포"),
    ELEPHANT(new ElephantMoveStrategy(), "상"),
    GUARD(new GuardMoveStrategy(), "사"),
    HORSE(new HorseMoveStrategy(), "마"),
    KING(new KingMoveStrategy(), "왕"),
    PAWN(new PawnMoveStrategy(), "졸"),
    ROOK(new RookMoveStrategy(), "차");

    private final MoveStrategy moveStrategy;
    private final String displayName;

    PieceType(MoveStrategy moveStrategy, String displayName) {
        this.moveStrategy = moveStrategy;
        this.displayName = displayName;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }

    public String getDisplayName() {
        return displayName;
    }
}

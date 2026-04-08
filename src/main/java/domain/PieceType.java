package domain;

import strategy.move.CannonMoveStrategy;
import strategy.move.ElephantMoveStrategy;
import strategy.move.HorseMoveStrategy;
import strategy.move.KingAndGuardMoveStrategy;
import strategy.move.MoveStrategy;
import strategy.move.PawnMoveStrategy;
import strategy.move.RookMoveStrategy;

public enum PieceType {
    CANNON(new CannonMoveStrategy(), "포", MaterialPoints.of(7)),
    ELEPHANT(new ElephantMoveStrategy(), "상", MaterialPoints.of(3)),
    GUARD(new KingAndGuardMoveStrategy(), "사", MaterialPoints.of(3)),
    HORSE(new HorseMoveStrategy(), "마", MaterialPoints.of(5)),
    KING(new KingAndGuardMoveStrategy(), "왕", MaterialPoints.zero()),
    PAWN(new PawnMoveStrategy(), "졸", MaterialPoints.of(2)),
    ROOK(new RookMoveStrategy(), "차", MaterialPoints.of(13));

    private final MoveStrategy moveStrategy;
    private final String displayName;
    private final MaterialPoints materialPoints;

    PieceType(MoveStrategy moveStrategy, String displayName, MaterialPoints materialPoints) {
        this.moveStrategy = moveStrategy;
        this.displayName = displayName;
        this.materialPoints = materialPoints;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }

    public String getDisplayName() {
        return displayName;
    }

    public MaterialPoints materialPoints() {
        return materialPoints;
    }
}

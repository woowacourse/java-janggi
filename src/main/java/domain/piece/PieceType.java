package domain.piece;

import domain.BoardLocation;
import domain.piece.strategy.CannonMoveStrategy;
import domain.piece.strategy.ChariotMoveStrategy;
import domain.piece.strategy.ChoPawnMoveStrategy;
import domain.piece.strategy.ElephantMoveStrategy;
import domain.piece.strategy.HanPawnMoveStrategy;
import domain.piece.strategy.HorseMoveStrategy;
import domain.piece.strategy.KingMoveStrategy;
import domain.piece.strategy.ScholarMoveStrategy;

public enum PieceType {
    CHARIOT(new ChariotMoveStrategy()),
    CANNON(new CannonMoveStrategy()),
    HORSE(new HorseMoveStrategy()),
    ELEPHANT(new ElephantMoveStrategy()),
    SCHOLAR(new ScholarMoveStrategy()),
    KING(new KingMoveStrategy()),
    HAN_PAWN(new HanPawnMoveStrategy()),
    CHO_PAWN(new ChoPawnMoveStrategy()),;
    private final MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public boolean isMovable(BoardLocation current, BoardLocation target) {
        return this.moveStrategy.isMovable(current, target);
    }
}

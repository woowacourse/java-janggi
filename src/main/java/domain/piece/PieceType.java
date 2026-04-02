package domain.piece;

import domain.piece.strategy.ChariotMoveStrategy;
import domain.piece.strategy.GeneralMoveStrategy;
import domain.piece.strategy.MoveStrategy;

public enum PieceType {
    GENERAL(new GeneralMoveStrategy()),
    GUARD(new GeneralMoveStrategy()),
    CHARIOT(new ChariotMoveStrategy()),
    CANNON(new ChariotMoveStrategy()),
    HORSE(new ChariotMoveStrategy()),
    ELEPHANT(new ChariotMoveStrategy()),
    SOLDIER(new ChariotMoveStrategy());

    private MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public MoveStrategy moveStrategy() {
        return moveStrategy;
    }
}

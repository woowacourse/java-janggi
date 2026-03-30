package domain.piece;

import domain.piece.strategy.*;

public enum PieceType {
    GENERAL(new GeneralAndGuardStrategy()),
    GUARD(new GeneralAndGuardStrategy()),
    HORSE(new HorseStrategy()),
    ELEPHANT(new ElephantStrategy()),
    CHARIOT(new ChariotStrategy()),
    CANNON(new CannonStrategy()),
    SOLDIER(new SoldierStrategy());

    private final MoveStrategy moveStrategy;

    PieceType(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}

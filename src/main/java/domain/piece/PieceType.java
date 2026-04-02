package domain.piece;

import domain.piece.strategy.*;

import java.util.function.Function;

public enum PieceType {
    GENERAL(camp -> new GeneralMoveStrategy()),
    GUARD(camp -> new GeneralMoveStrategy()),
    CHARIOT(camp -> new ChariotMoveStrategy()),
    CANNON(camp -> new CannonMoveStrategy()),
    HORSE(camp -> new ChariotMoveStrategy()),
    ELEPHANT(camp -> new ChariotMoveStrategy()),
    SOLDIER(camp -> new SoldierMoveStrategy(camp.getForwardDirection()));

    private final Function<Camp, MoveStrategy> strategyFactory;

    PieceType(Function<Camp, MoveStrategy> strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public MoveStrategy moveStrategy(Camp camp) {
        return strategyFactory.apply(camp);
    }
}

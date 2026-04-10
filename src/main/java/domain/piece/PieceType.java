package domain.piece;

import domain.piece.strategy.*;

import java.util.function.Function;

public enum PieceType {
    GENERAL(0, camp -> new GeneralMoveStrategy()),
    GUARD(3, camp -> new GeneralMoveStrategy()),
    CHARIOT(13, camp -> new ChariotMoveStrategy()),
    CANNON(7, camp -> new CannonMoveStrategy()),
    HORSE(5, camp -> new HorseMoveStrategy()),
    ELEPHANT(3, camp -> new ElephantMoveStrategy()),
    SOLDIER(2, camp -> new SoldierMoveStrategy(camp.getForwardDirection()));

    private final double score;
    private final Function<Camp, MoveStrategy> strategyFactory;

    PieceType(double score, Function<Camp, MoveStrategy> strategyFactory) {
        this.score = score;
        this.strategyFactory = strategyFactory;
    }

    public MoveStrategy moveStrategy(Camp camp) {
        return strategyFactory.apply(camp);
    }

    public double score() {
        return score;
    }
}

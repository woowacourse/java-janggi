package domain;

import domain.strategy.CannonMoveStrategy;
import domain.strategy.ChariotMoveStrategy;
import domain.strategy.GreenSoldierMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.GeneralMoveStrategy;
import domain.strategy.GuardMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.MoveStrategy;
import domain.strategy.NoneMoveableStrategy;
import domain.strategy.UpToDownSoldierMoveStrategy;
import java.util.function.Function;

public enum PieceType {

    GENERAL("將", GeneralMoveStrategy::of),
    GUARD("士", GuardMoveStrategy::of),

    HORSE("馬", HorseMoveStrategy::of),
    ELEPHANT("象", ElephantMoveStrategy::of),
    CHARIOT("車", ChariotMoveStrategy::of),
    CANNON("包", CannonMoveStrategy::of),

    RED_SOLDIER("卒", UpToDownSoldierMoveStrategy::of),
    GREEN_SOLDIER("卒", GreenSoldierMoveStrategy::of),

    EMPTY_VALUE("＋", NoneMoveableStrategy::of)
    ;

    private final String description;
    private final Function<Position, MoveStrategy> strategyOfPosition;

    PieceType(String description, Function<Position, MoveStrategy> strategyOfPosition) {
        this.description = description;
        this.strategyOfPosition = strategyOfPosition;
    }

    public String description() {
        return this.description;
    }

    public MoveStrategy createStrategy(Position position) {
        return strategyOfPosition.apply(position);
    }
}

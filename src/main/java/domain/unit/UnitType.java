package domain.unit;

import domain.unit.move.ElephantMovingStrategy;
import domain.unit.move.HorseMovingStrategy;
import domain.unit.move.MovingStrategy;
import domain.unit.move.OneStepMovingStrategy;
import domain.unit.move.StraightMovingStrategy;
import java.util.function.Supplier;

public enum UnitType {

    GENERAL(0, OneStepMovingStrategy::new),
    GUARD(3, OneStepMovingStrategy::new),
    CHARIOT(13, StraightMovingStrategy::new),
    CANNON(7, StraightMovingStrategy::new),
    SOLDIER(2, OneStepMovingStrategy::new),
    HORSE(5, HorseMovingStrategy::new),
    ELEPHANT(3, ElephantMovingStrategy::new),
    ;

    private final int score;
    private final Supplier<MovingStrategy> rule;

    UnitType(int score, Supplier<MovingStrategy> rule) {
        this.score = score;
        this.rule = rule;
    }

    public Unit createUnit(Team team) {
        return Unit.of(team, this.rule.get(), this);
    }

    public boolean canNotMoveOutOfPalace() {
        return (this == GENERAL || this == GUARD);
    }

    public int getScore() {
        return score;
    }
}

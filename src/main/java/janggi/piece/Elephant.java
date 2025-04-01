package janggi.piece;

import janggi.moveStrategy.MoveStrategy;
import janggi.moveStrategy.SlideStrategy;
import janggi.moveStrategy.rule.MovingRules;
import janggi.moveStrategy.rule.MovingRulesGenerator;

public final class Elephant extends Piece {

    private Elephant(final Team team, final MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    public static Elephant of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.elephant();
        return new Elephant(team, new SlideStrategy(movingRules));
    }

    @Override
    public Type getType() {
        return Type.ELEPHANT;
    }
}

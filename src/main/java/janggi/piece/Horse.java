package janggi.piece;

import janggi.moveStrategy.MoveStrategy;
import janggi.moveStrategy.SlideStrategy;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Horse extends Piece {

    private Horse(final Team team, final MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    public static Horse of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.horse();
        return new Horse(team, new SlideStrategy(movingRules));
    }

    @Override
    public Type getType() {
        return Type.HORSE;
    }
}

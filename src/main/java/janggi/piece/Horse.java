package janggi.piece;

import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Horse extends Piece {

    private Horse(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Horse of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.horse();
        return new Horse(team, movingRules);
    }

    @Override
    public Type getType() {
        return Type.HORSE;
    }
}

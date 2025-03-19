package janggi.piece;

import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import java.util.List;

public final class Horse extends UnjumpablePiece {

    private Horse(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Horse of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.horse();
        return new Horse(team, movingRules);
    }
}

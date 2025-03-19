package janggi.piece;

import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import java.util.List;

public final class Elephant extends UnjumpablePiece {

    public Elephant(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Elephant of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.elephant();
        return new Elephant(team, movingRules);
    }
}

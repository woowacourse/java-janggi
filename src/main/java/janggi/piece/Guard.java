package janggi.piece;

import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import java.util.List;

public final class Guard extends Piece {

    public Guard(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Guard of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.generalOrGuard();
        return new Guard(team, movingRules);
    }
}

package janggi.piece;

import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import java.util.List;

public final class General extends Piece {

    public General(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static General of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.generalOrGuard();
        return new General(team, movingRules);
    }
}

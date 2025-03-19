package janggi.piece;

import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import java.util.List;

public final class Cannon extends JumpablePiece {

    public Cannon(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Cannon of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.cannonOrChariot();
        return new Cannon(team, movingRules);
    }
}

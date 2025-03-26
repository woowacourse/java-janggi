package janggi.piece;

import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Elephant extends Piece {

    private Elephant(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Elephant of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.elephant();
        return new Elephant(team, movingRules);
    }

    @Override
    public Type getType() {
        return Type.ELEPHANT;
    }
}

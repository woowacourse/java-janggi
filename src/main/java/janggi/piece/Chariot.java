package janggi.piece;

import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Chariot extends Piece {

    private Chariot(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Chariot of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.cannonOrChariot();
        return new Chariot(team, movingRules);
    }

    @Override
    public Type getType() {
        return Type.CHARIOT;
    }
}

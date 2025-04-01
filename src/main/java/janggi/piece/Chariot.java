package janggi.piece;

import janggi.moveStrategy.MoveStrategy;
import janggi.moveStrategy.SlidePalaceStrategy;
import janggi.moveStrategy.rule.MovingRules;
import janggi.moveStrategy.rule.MovingRulesGenerator;

public final class Chariot extends Piece {

    private Chariot(final Team team, final MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    public static Chariot of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.cannonOrChariot();
        return new Chariot(team, new SlidePalaceStrategy(movingRules));
    }

    @Override
    public Type getType() {
        return Type.CHARIOT;
    }
}

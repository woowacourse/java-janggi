package janggi.piece;

import janggi.moveStrategy.MoveStrategy;
import janggi.moveStrategy.OnlyPalaceStrategy;
import janggi.moveStrategy.rule.MovingRules;
import janggi.moveStrategy.rule.MovingRulesGenerator;

public final class Guard extends Piece {

    private Guard(final Team team, final MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    public static Guard of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.generalOrGuard();
        return new Guard(team, new OnlyPalaceStrategy(movingRules));
    }

    @Override
    public Type getType() {
        return Type.GUARD;
    }
}

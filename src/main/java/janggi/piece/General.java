package janggi.piece;

import janggi.moveStrategy.MoveStrategy;
import janggi.moveStrategy.OnlyPalaceStrategy;
import janggi.moveStrategy.rule.MovingRules;
import janggi.moveStrategy.rule.MovingRulesGenerator;

public final class General extends Piece {

    private General(final Team team, final MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    public static General of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.generalOrGuard();
        return new General(team, new OnlyPalaceStrategy(movingRules));
    }

    @Override
    public Type getType() {
        return Type.GENERAL;
    }
}

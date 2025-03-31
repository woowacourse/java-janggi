package janggi.piece;

import janggi.moveStrategy.MoveStrategy;
import janggi.moveStrategy.OnlyPalaceStrategy;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

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

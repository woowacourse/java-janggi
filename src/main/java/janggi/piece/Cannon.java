package janggi.piece;

import janggi.moveStrategy.JumpStrategy;
import janggi.moveStrategy.MoveStrategy;
import janggi.moveStrategy.rule.MovingRules;
import janggi.moveStrategy.rule.MovingRulesGenerator;

public final class Cannon extends Piece {

    private static final int CANNON_CROSS_COUNT = 1;

    private Cannon(final Team team, final MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    public static Cannon of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.cannonOrChariot();
        return new Cannon(team, new JumpStrategy(movingRules));
    }

    @Override
    public Type getType() {
        return Type.CANNON;
    }
}

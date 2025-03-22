package janggi.piece;

import janggi.position.Position;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;
import java.util.Map;

public final class Guard extends Piece {

    private Guard(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Guard of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.generalOrGuard();
        return new Guard(team, movingRules);
    }

    @Override
    public void validateMove(final Position start, final Position end, final Map<Position, Piece> board) {
    }

    @Override
    public Type type() {
        return Type.GUARD;
    }
}

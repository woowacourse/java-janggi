package janggi.piece;

import janggi.position.Position;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;
import java.util.Map;

public final class General extends Piece {

    private General(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static General of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.generalOrGuard();
        return new General(team, movingRules);
    }

    @Override
    public void validateMove(final Position start, final Position end, final Map<Position, Piece> board) {
    }

    @Override
    public Type type() {
        return Type.GENERAL;
    }
}

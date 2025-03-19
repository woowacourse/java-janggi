package janggi.piece;

import janggi.Board;
import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import janggi.Position;
import java.util.List;

public final class General extends UnjumpablePiece {

    public General(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static General of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.generalOrGuard();
        return new General(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        return false;
    }
}

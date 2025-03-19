package janggi.piece;

import janggi.Board;
import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import janggi.Position;
import java.util.List;

public final class Guard extends Piece {

    public Guard(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Guard of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.generalOrGuard();
        return new Guard(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        return false;
    }
}

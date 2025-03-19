package janggi.piece;

import janggi.Board;
import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import janggi.Position;
import java.util.List;

public final class Cannon extends Piece {

    public Cannon(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Cannon of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.cannonOrChariot();
        return new Cannon(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        return false;
    }
}

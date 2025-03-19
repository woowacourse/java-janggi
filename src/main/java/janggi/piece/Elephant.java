package janggi.piece;

import janggi.Board;
import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import janggi.Position;
import java.util.List;

public final class Elephant extends Piece {

    public Elephant(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Elephant of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.elephant();
        return new Elephant(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        return false;
    }
}

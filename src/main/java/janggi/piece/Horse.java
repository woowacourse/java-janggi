package janggi.piece;

import janggi.Board;
import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import janggi.Position;
import java.util.List;

public final class Horse extends Piece {

    public Horse(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Horse of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.horse();
        return new Horse(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        return false;
    }
}

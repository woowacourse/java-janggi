package janggi.piece;

import janggi.Board;
import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import janggi.Position;
import java.util.List;


public final class Chariot extends Piece {

    public Chariot(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Chariot of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.cannonOrChariot();
        return new Chariot(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        return false;
    }
}

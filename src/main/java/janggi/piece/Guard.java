package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Guard extends Piece {

    private Guard(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Guard of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.generalOrGuard();
        return new Guard(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        if (movingRules.cannotFindRule(start, end)) {
            return false;
        }
        return !isPresentSameTeam(end, board);
    }

    @Override
    public Type type() {
        return Type.GUARD;
    }

    private boolean isPresentSameTeam(final Position end, final Board board) {
        return board.isPresentSameTeam(team, end);
    }
}

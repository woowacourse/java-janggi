package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MovingRule;
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
    protected boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        if (!end.isPalace()) {
            return true;
        }
        final MovingRule matchRule = movingRules.findMatchRule(start, end);
        if (matchRule.isDiagonal()) {
            return !start.isCenterOfPalace() && !end.isCenterOfPalace();
        }
        return false;
    }

    @Override
    protected boolean isValidDestination(final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end);
    }

    @Override
    public Type getType() {
        return Type.GUARD;
    }
}

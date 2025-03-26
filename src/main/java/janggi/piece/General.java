package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MovingRule;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class General extends Piece {

    private General(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static General of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.generalOrGuard();
        return new General(team, movingRules);
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
    public Type getType() {
        return Type.GENERAL;
    }
}

package janggi.moveStrategy;

import janggi.board.Board;
import janggi.moveStrategy.rule.MovingRule;
import janggi.moveStrategy.rule.MovingRules;
import janggi.piece.Team;
import janggi.position.Position;

public class OnlyPalaceStrategy implements MoveStrategy {

    private final MovingRules movingRules;

    public OnlyPalaceStrategy(final MovingRules movingRules) {
        this.movingRules = movingRules;
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Team team, final Board board) {
        if (!movingRules.findRule(start, end)) {
            return false;
        }
        if (cannotMoveThrough(start, end, board)) {
            return false;
        }
        return isValidDestination(team, end, board);
    }

    private boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        if (!end.isPalace()) {
            return true;
        }
        final MovingRule matchRule = movingRules.getMatchedRule();
        if (matchRule.isDiagonal()) {
            return !start.isCenterOfPalace() && !end.isCenterOfPalace();
        }
        return false;
    }

    private boolean isValidDestination(final Team team, final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end);
    }
}

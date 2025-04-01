package janggi.moveStrategy;

import janggi.board.Board;
import janggi.moveStrategy.rule.MoveVector;
import janggi.moveStrategy.rule.MovingRule;
import janggi.moveStrategy.rule.MovingRules;
import janggi.piece.Team;
import janggi.position.Position;

public class JumpStrategy implements MoveStrategy {

    private static final int CROSS_COUNT = 1;

    private final MovingRules movingRules;

    public JumpStrategy(final MovingRules movingRules) {
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
        final MovingRule matchRule = movingRules.getMatchedRule();
        Position route = start;
        int count = 0;
        for (MoveVector vector : matchRule.getVectorsWithoutLast()) {
            route = route.add(vector);
            if (board.isExistCannon(route)) {
                return true;
            }
            if (board.isPresent(route)) {
                count++;
            }
        }
        return count != CROSS_COUNT;
    }

    private boolean isValidDestination(final Team team, final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end) && !board.isExistCannon(end);
    }
}

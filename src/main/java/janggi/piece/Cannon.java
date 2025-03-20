package janggi.piece;

import janggi.Board;
import janggi.MoveVector;
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
    public boolean canJump() {
        return true;
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        if (cannotFindRule(start, end)) {
            return false;
        }
        if (cannotMoveThrough(start, end, board)) {
            return false;
        }
        return isNotSameTeamAndNotJumpable(end, board);
    }

    private boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        final MovingRule matchRule = findMatchRule(start, end);
        Position route = start;
        int count = 0;
        for (MoveVector vector : matchRule.getVectorsWithoutLast()) {
            route = route.add(vector);
            if (board.isUnjumpablePiece(route)) {
                return true;
            }
            if (board.isPresent(route)) {
                count++;
            }
        }
        return count != 1;
    }

    private boolean isNotSameTeamAndNotJumpable(final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end) && !board.isUnjumpablePiece(end);
    }

    private MovingRule findMatchRule(final Position start, final Position end) {
        final MoveVector startEndDiff = end.getDiff(start);
        for (MovingRule movingRule : movingRules) {
            final MoveVector vectorSum = movingRule.sumUnit();
            if (vectorSum.equals(startEndDiff)) {
                return movingRule;
            }
        }
        throw new IllegalStateException();
    }
}

package janggi.piece;

import janggi.Board;
import janggi.MoveVector;
import janggi.MovingRule;
import janggi.Position;
import java.util.List;

public abstract class JumpablePiece extends Piece {

    public JumpablePiece(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
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
        if (cannotMoveThrough(start, end, board)) { // 포가 지나치고 있는게 포가 아닌 기물 하나인지 체크
            return false;
        }
        return isNotSameTeamAndNotJumpable(end, board); // 내팀도 안되고, 상대 포도 안되고
    }

    private boolean isNotSameTeamAndNotJumpable(final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end) && !board.isUnjumpablePiece(end);
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

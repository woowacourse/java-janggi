package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MoveVector;
import janggi.rule.MovingRule;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Cannon extends Piece {

    public Cannon(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Cannon of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.cannonOrChariot();
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

    @Override
    public Type type() {
        return Type.CANNON;
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
        return movingRules.findMatchRule(startEndDiff);
    }
}

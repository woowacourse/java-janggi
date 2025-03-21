package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MoveVector;
import janggi.rule.MovingRule;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Cannon extends Piece {

    private static final int CANNON_CROSS_COUNT = 1;

    public Cannon(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Cannon of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.cannonOrChariot();
        return new Cannon(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        if (movingRules.cannotFindRule(start, end)) {
            return false;
        }
        if (cannotMoveThrough(start, end, board)) {
            return false;
        }
        return isValidDestination(end, board);
    }

    @Override
    public Type type() {
        return Type.CANNON;
    }

    private boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        final MovingRule matchRule = movingRules.findMatchRule(start, end);
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
        return count != CANNON_CROSS_COUNT;
    }

    private boolean isValidDestination(final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end) && !board.isExistCannon(end);
    }
}

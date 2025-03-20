package janggi.piece;

import janggi.board.Board;
import janggi.rule.MoveVector;
import janggi.rule.MovingRule;
import janggi.rule.MovingRulesGenerator;
import janggi.position.Position;
import java.util.List;

public final class Elephant extends Piece {

    private Elephant(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Elephant of(final Team team) {
        final List<MovingRule> movingRules = MovingRulesGenerator.elephant();
        return new Elephant(team, movingRules);
    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        if (cannotFindRule(start, end)) {
            return false;
        }
        if (cannotMoveThrough(start, end, board)) {
            return false;
        }
        return !isPresentSameTeam(end, board);
    }

    @Override
    public Type type() {
        return Type.ELEPHANT;
    }

    private boolean isPresentSameTeam(final Position end, final Board board) {
        return board.isPresentSameTeam(team, end);
    }

    private boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        final MovingRule matchRule = findMatchRule(start, end);
        Position route = start;
        for (MoveVector vector : matchRule.getVectorsWithoutLast()) {
            route = route.add(vector);
            if (board.isPresent(route)) {
                return true;
            }
        }
        return false;
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

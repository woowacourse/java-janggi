package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MoveVector;
import janggi.rule.MovingRule;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Horse extends Piece {

    private Horse(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Horse of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.horse();
        return new Horse(team, movingRules);
    }

    @Override
    protected boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        final MovingRule matchRule = movingRules.findMatchRule(start, end);
        Position route = start;
        for (MoveVector vector : matchRule.getVectorsWithoutLast()) {
            route = route.add(vector);
            if (board.isPresent(route)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isValidDestination(final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end);
    }

    @Override
    public Type getType() {
        return Type.HORSE;
    }
}

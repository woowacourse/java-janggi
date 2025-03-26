package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MoveVector;
import janggi.rule.MovingRule;
import janggi.rule.MovingRules;

public sealed abstract class Piece permits Cannon, Chariot, Elephant, General, Guard, Horse, Soldier {

    protected final Team team;
    protected final MovingRules movingRules;

    public Piece(final Team team, final MovingRules movingRules) {
        this.team = team;
        this.movingRules = movingRules;
    }

    public boolean canMove(final Position start, final Position end, final Board board) {
        if (movingRules.cannotFindRule(start, end)) {
            return false;
        }
        if (cannotMoveThrough(start, end, board)) {
            return false;
        }
        return isValidDestination(end, board);
    }

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

    protected boolean isValidDestination(final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end);
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract Type getType();
}

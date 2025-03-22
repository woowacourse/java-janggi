package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
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

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    protected abstract boolean cannotMoveThrough(final Position start, final Position end, final Board board);

    protected abstract boolean isValidDestination(final Position end, final Board board);

    public abstract Type getType();
}

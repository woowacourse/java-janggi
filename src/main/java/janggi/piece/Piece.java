package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MoveVector;
import janggi.rule.MovingRules;
import java.util.List;

public abstract class Piece {

    protected final Team team;
    protected final MovingRules movingRules;

    public Piece(final Team team, final MovingRules movingRules) {
        this.team = team;
        this.movingRules = movingRules;
    }

    public abstract boolean canJump();

    public abstract boolean canMove(final Position start, final Position end, final Board board);

    public abstract Type type();

    public boolean isHan() {
        return team == Team.HAN;
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    protected boolean cannotFindRule(final Position start, final Position end) {
        final List<MoveVector> positionDiffs = movingRules.getRuleVectors();
        return !positionDiffs.contains(end.calculateMoveVector(start));
    }
}

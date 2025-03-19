package janggi.piece;

import janggi.Board;
import janggi.MoveVector;
import janggi.MovingRule;
import janggi.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    protected final Team team;
    protected final List<MovingRule> movingRules;

    public Piece(final Team team, final List<MovingRule> movingRules) {
        this.team = team;
        this.movingRules = movingRules;
    }

    public abstract boolean canJump();

    public abstract boolean canMove(final Position start, final Position end, final Board board);

    public boolean isHan() {
        return team == Team.HAN;
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }

    protected boolean cannotFindRule(final Position start, final Position end) {
        final List<MoveVector> positionDiffs = new ArrayList<>();
        for (MovingRule movingRule : movingRules) {
            positionDiffs.add(movingRule.sumUnit());
        }
        return !positionDiffs.contains(end.getDiff(start));
    }
}

package janggi.piece;

import janggi.Board;
import janggi.MovingRule;
import janggi.Position;
import java.util.List;

public abstract class Piece {

    protected final Team team;
    protected final List<MovingRule> movingRules;

    public Piece(final Team team, final List<MovingRule> movingRules) {
        this.team = team;
        this.movingRules = movingRules;
    }

    public abstract boolean canMove(final Position start, final Position end, final Board board);

    public boolean isHan() {
        return team == Team.HAN;
    }

    public boolean isSameTeam(final Team team) {
        return this.team == team;
    }
}

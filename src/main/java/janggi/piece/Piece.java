package janggi.piece;

import janggi.position.Position;
import janggi.rule.MovingRules;
import java.util.Map;

public abstract class Piece {

    protected final Team team;
    protected final MovingRules movingRules;

    public Piece(final Team team, final MovingRules movingRules) {
        this.team = team;
        this.movingRules = movingRules;
    }

    public abstract void validateMove(final Position start, final Position end, final Map<Position, Piece> board);

    public abstract Type type();

    public final boolean isHan() {
        return team == Team.HAN;
    }

    public final boolean isSameTeam(final Piece other) {
        return other.team == this.team;
    }
}

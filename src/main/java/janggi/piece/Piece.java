package janggi.piece;

import janggi.MovingRule;
import java.util.List;

public abstract class Piece {

    private final Team team;
    private final List<MovingRule> movingRules;

    public Piece(final Team team, final List<MovingRule> movingRules) {
        this.team = team;
        this.movingRules = movingRules;
    }

    public boolean isHan() {
        return team == Team.HAN;
    }
}

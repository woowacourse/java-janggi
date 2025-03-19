package janggi.piece;

import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import java.util.List;

public final class Soldier extends UnjumpablePiece {

    public Soldier(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Soldier of(final Team team) {
        if (team == Team.HAN) {
            return new Soldier(Team.HAN, MovingRulesGenerator.hanSoldier());
        }
        return new Soldier(Team.CHO, MovingRulesGenerator.choSoldier());
    }
}

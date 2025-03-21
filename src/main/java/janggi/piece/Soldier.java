package janggi.piece;

import janggi.position.Position;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;
import java.util.Map;

public final class Soldier extends Piece {

    private Soldier(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Soldier of(final Team team) {
        if (team == Team.HAN) {
            return new Soldier(Team.HAN, MovingRulesGenerator.hanSoldier());
        }
        return new Soldier(Team.CHO, MovingRulesGenerator.choSoldier());
    }

    @Override
    public void validateMove(final Position start, final Position end, final Map<Position, Piece> board) {
    }

    @Override
    public Type type() {
        return Type.SOLDIER;
    }
}

package janggi.piece;

import janggi.moveStrategy.MoveStrategy;
import janggi.moveStrategy.SlidePalaceStrategy;
import janggi.moveStrategy.rule.MovingRulesGenerator;

public final class Soldier extends Piece {

    private Soldier(final Team team, final MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    public static Soldier of(final Team team) {
        if (team == Team.HAN) {
            return new Soldier(Team.HAN, new SlidePalaceStrategy(MovingRulesGenerator.hanSoldier()));
        }
        if (team == Team.CHO) {
            return new Soldier(Team.CHO, new SlidePalaceStrategy(MovingRulesGenerator.choSoldier()));
        }
        throw new IllegalStateException("[ERROR] 병의 팀이 선택되지 않았습니다.");
    }

    @Override
    public Type getType() {
        return Type.SOLDIER;
    }
}

package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MovingRule;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class Soldier extends Piece {

    private Soldier(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static Soldier of(final Team team) {
        if (team == Team.HAN) {
            return new Soldier(Team.HAN, MovingRulesGenerator.hanSoldier());
        }
        if (team == Team.CHO) {
            return new Soldier(Team.CHO, MovingRulesGenerator.choSoldier());
        }
        throw new IllegalStateException("[ERROR] 병의 팀이 선택되지 않았습니다.");
    }

    @Override
    protected boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        final MovingRule matchRule = movingRules.findMatchRule(start, end);
        if (matchRule.isDiagonal()) {
            return !start.isCenterOfPalace() && !end.isCenterOfPalace();
        }
        return false;
    }

    @Override
    protected boolean isValidDestination(final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end);
    }

    @Override
    public Type getType() {
        return Type.SOLDIER;
    }
}

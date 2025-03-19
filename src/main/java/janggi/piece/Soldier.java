package janggi.piece;

import janggi.Board;
import janggi.MoveVector;
import janggi.MovingRule;
import janggi.MovingRulesGenerator;
import janggi.Position;
import java.util.ArrayList;
import java.util.List;

public final class Soldier extends Piece {

    public Soldier(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Soldier of(final Team team) {
        if (team == Team.HAN) {
            return new Soldier(Team.HAN, MovingRulesGenerator.hanSoldier());
        }
        return new Soldier(Team.CHO, MovingRulesGenerator.choSoldier());
    }

    public boolean canMove(final Position start, final Position end, final Board board) {
        if (cannotFindRule(start, end)) {
            return false;
        }
        return !board.isPresentSameTeam(team, end);
    }

    private boolean cannotFindRule(final Position start, final Position end) {
        final List<MoveVector> positionDiffs = new ArrayList<>();
        for (MovingRule movingRule : movingRules) {
            positionDiffs.add(movingRule.sumUnit());
        }
        return !positionDiffs.contains(end.getDiff(start));
    }
}

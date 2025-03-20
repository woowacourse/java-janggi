package janggi.piece;

import janggi.board.Board;
import janggi.rule.MovingRule;
import janggi.rule.MovingRulesGenerator;
import janggi.position.Position;
import java.util.List;

public final class Soldier extends Piece {

    private Soldier(final Team team, final List<MovingRule> movingRules) {
        super(team, movingRules);
    }

    public static Soldier of(final Team team) {
        if (team == Team.HAN) {
            return new Soldier(Team.HAN, MovingRulesGenerator.hanSoldier());
        }
        return new Soldier(Team.CHO, MovingRulesGenerator.choSoldier());
    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        if (cannotFindRule(start, end)) {
            return false;
        }
        return !isPresentSameTeam(end, board);
    }

    @Override
    public Type type() {
        return Type.SOLDIER;
    }

    private boolean isPresentSameTeam(final Position end, final Board board) {
        return board.isPresentSameTeam(team, end);
    }
}

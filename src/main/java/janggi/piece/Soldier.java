package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
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
        return new Soldier(Team.CHO, MovingRulesGenerator.choSoldier());
    }

    @Override
    protected boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
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

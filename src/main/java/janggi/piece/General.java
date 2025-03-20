package janggi.piece;

import janggi.board.Board;
import janggi.position.Position;
import janggi.rule.MovingRules;
import janggi.rule.MovingRulesGenerator;

public final class General extends Piece {

    private General(final Team team, final MovingRules movingRules) {
        super(team, movingRules);
    }

    public static General of(final Team team) {
        final MovingRules movingRules = MovingRulesGenerator.generalOrGuard();
        return new General(team, movingRules);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Board board) {
        if (movingRules.cannotFindRule(start, end)) {
            return false;
        }
        return !isPresentSameTeam(end, board);
    }

    @Override
    public Type type() {
        return Type.GENERAL;
    }

    private boolean isPresentSameTeam(final Position end, final Board board) {
        return board.isPresentSameTeam(team, end);
    }
}

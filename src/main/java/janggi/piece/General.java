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
    protected boolean cannotMoveThrough(final Position start, final Position end, final Board board) {
        return false;
    }

    @Override
    protected boolean isValidDestination(final Position end, final Board board) {
        return !board.isPresentSameTeam(team, end);
    }

    @Override
    public Type getType() {
        return Type.GENERAL;
    }
}

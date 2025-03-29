package janggi.piece;

import janggi.Board;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.piece.rule.movement.MovementRule;
import janggi.piece.rule.movement.SingleMovementRule;

public class General extends Piece {

    public General(final Position position,
                   final Team team,
                   final MovementRule movementRule) {
        super(position, team, movementRule);
    }

    public static General of(final Position position, final Team team) {
        return new General(position, team, SingleMovementRule.withNonBlock());
    }

    public static General defaultOf(final Team team) {
        final int defaultRow = Team.decideRow(2, team);
        final int defaultColumn = 5;

        return General.of(Position.of(defaultRow, defaultColumn), team);
    }

    @Override
    protected Piece createPiece(final Position position) {
        return General.of(position, team);
    }

    @Override
    protected void validateSpecialRule(final Board board, final Position destination) {
        if (board.isPalace(destination)) {
            return;
        }
        throw new IllegalArgumentException("궁은 궁성 밖으로 나갈 수 없습니다");
    }

    @Override
    public PieceType getType() {
        return PieceType.GENERAL;
    }
}

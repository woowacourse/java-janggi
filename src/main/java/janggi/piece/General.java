package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.piece.rule.movement.MovementRule;
import janggi.piece.rule.movement.SingleMovementRule;
import janggi.piece.rule.palace.PalaceRestrictRule;
import janggi.player.Team;

public class General extends Piece {

    private final PalaceRestrictRule palaceRestrictRule;

    public General(final Position position,
                   final Team team,
                   final MovementRule movementRule,
                   final PalaceRestrictRule palaceRestrictRule) {
        super(position, team, movementRule);
        this.palaceRestrictRule = palaceRestrictRule;
    }

    public static General of(final Position position, final Team team) {
        return new General(position, team, SingleMovementRule.withNonBlock(), new PalaceRestrictRule());
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
        palaceRestrictRule.validate(board, destination);
    }

    @Override
    public PieceType getType() {
        return PieceType.GENERAL;
    }
}

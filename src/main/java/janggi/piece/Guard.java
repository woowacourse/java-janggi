package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.piece.rule.movement.MovementRule;
import janggi.piece.rule.movement.SingleMovementRule;
import janggi.piece.rule.palace.PalaceRestrictRule;
import janggi.player.Team;

import java.util.List;

public class Guard extends Piece {

    private final PalaceRestrictRule palaceRestrictRule;

    public Guard(final Position position,
                 final Team team,
                 final MovementRule movementRule,
                 final PalaceRestrictRule palaceRestrictRule) {
        super(position, team, movementRule);
        this.palaceRestrictRule = palaceRestrictRule;
    }

    public static Guard of(final Position position, final Team team) {
        return new Guard(position, team, SingleMovementRule.withNonBlock(), new PalaceRestrictRule());
    }

    public static List<Guard> defaultsOf(final Team team) {
        final int defaultRow = Team.decideRow(1, team);
        final List<Integer> defaultColumns = List.of(4, 6);

        return defaultColumns.stream()
                .map(defaultColumn -> Guard.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Guard.of(position, team);
    }

    @Override
    protected void validateSpecialRule(final Board board, final Position destination) {
        palaceRestrictRule.validate(board, destination);
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }
}

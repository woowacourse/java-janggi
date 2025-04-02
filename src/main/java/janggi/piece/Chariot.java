package janggi.piece;

import janggi.coordinate.Position;
import janggi.piece.rule.movement.MovementRule;
import janggi.piece.rule.movement.StraightMovementRule;
import janggi.player.Team;

import java.util.List;

public class Chariot extends Piece {

    private Chariot(final Position position,
                    final Team team,
                    final MovementRule movementRule) {
        super(position, team, movementRule);
    }

    public static Chariot of(final Position position, final Team team) {
        return new Chariot(position, team, StraightMovementRule.withNonBlock());
    }

    public static List<Chariot> defaultsOf(final Team team) {
        final int defaultRow = Team.decideRow(1, team);
        final List<Integer> defaultColumns = List.of(1, 9);

        return defaultColumns.stream()
                .map(defaultColumn -> Chariot.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Chariot.of(position, team);
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }
}

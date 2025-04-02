package janggi.piece;

import janggi.coordinate.Position;
import janggi.piece.rule.movement.CurvedMovementRule;
import janggi.piece.rule.movement.MovementRule;
import janggi.player.Team;

import java.util.List;

public class Horse extends Piece {

    public static final int STRAIGHT_MOVEMENT = 1;
    public static final int DIAGONAL_MOVEMENT = 1;

    public Horse(final Position position,
                 final Team team,
                 final MovementRule movementRule) {
        super(position, team, movementRule);
    }

    public static Horse of(final Position position, final Team team) {
        return new Horse(position, team, CurvedMovementRule.withNonBlock(STRAIGHT_MOVEMENT, DIAGONAL_MOVEMENT));
    }

    public static List<Horse> defaultsOf(final Team team) {
        final int defaultRow = Team.decideRow(1, team);
        final List<Integer> defaultColumns = List.of(3, 8);

        return defaultColumns.stream()
                .map(defaultColumn -> Horse.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Horse.of(position, team);
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }
}

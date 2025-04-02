package janggi.piece;

import janggi.coordinate.Position;
import janggi.piece.rule.movement.CurvedMovementRule;
import janggi.piece.rule.movement.MovementRule;
import janggi.player.Team;

import java.util.List;

public class Elephant extends Piece {

    public static final int STRAIGHT_MOVEMENT = 1;
    public static final int DIAGONAL_MOVEMENT = 2;

    public Elephant(final Position position,
                    final Team team,
                    final MovementRule movementRule) {
        super(position, team, movementRule);
    }

    public static Elephant of(final Position position, final Team team) {
        return new Elephant(position, team, CurvedMovementRule.withNonBlock(STRAIGHT_MOVEMENT, DIAGONAL_MOVEMENT));
    }

    public static List<Elephant> defaultsOf(final Team team) {
        final int defaultRow = Team.decideRow(1, team);
        final List<Integer> defaultColumns = List.of(2, 7);

        return defaultColumns.stream()
                .map(defaultColumn -> Elephant.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Elephant.of(position, team);
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }
}

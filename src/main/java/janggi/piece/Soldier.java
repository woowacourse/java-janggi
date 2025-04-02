package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.coordinate.Vector;
import janggi.piece.rule.movement.MovementRule;
import janggi.piece.rule.movement.SingleMovementRule;
import janggi.player.Team;

import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Position position,
                   final Team team,
                   final MovementRule movementRule) {
        super(position, team, movementRule);
    }

    public static Soldier of(final Position position, final Team team) {
        return new Soldier(position, team, SingleMovementRule.withNonBlock());
    }

    public static List<Soldier> defaultsOf(final Team team) {
        final int defaultRow = Team.decideRow(4, team);
        final List<Integer> defaultColumns = List.of(1, 3, 5, 7, 9);

        return defaultColumns.stream()
                .map(defaultColumn -> Soldier.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Soldier.of(position, team);
    }

    @Override
    protected void validateSpecialRule(final Board board, final Position destination) {
        final Vector vector = Vector.of(position, destination);

        if (team.isHan() && vector.isUpward()) {
            throw new IllegalArgumentException("병은 본진을 향할 수 없습니다.");
        }

        if (team.isCho() && vector.isDownward()) {
            throw new IllegalArgumentException("졸은 본진을 향할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}

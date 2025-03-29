package janggi.piece;

import janggi.board.Board;
import janggi.player.Team;
import janggi.coordinate.Position;
import janggi.piece.rule.movement.MovementRule;
import janggi.piece.rule.movement.SingleMovementRule;

import java.util.List;

public class Guard extends Piece {

    public Guard(final Position position,
                 final Team team,
                 final MovementRule movementRule) {
        super(position, team, movementRule);
    }

    public static Guard of(final Position position, final Team team) {
        return new Guard(position, team, SingleMovementRule.withNonBlock());
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
        if (board.isPalace(destination)) {
            return;
        }
        throw new IllegalArgumentException("사는 궁성 밖으로 나갈 수 없습니다");
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }
}

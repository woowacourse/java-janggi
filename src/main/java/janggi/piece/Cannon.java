package janggi.piece;

import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.coordinate.Route;
import janggi.piece.rule.movement.MovementRule;
import janggi.piece.rule.movement.StraightMovementRule;
import janggi.player.Team;

import java.util.List;

public class Cannon extends Piece {

    public static final int REQUIRE_BLOCK_COUNT = 1;

    private Cannon(final Position position,
                   final Team team,
                   final MovementRule movementRule) {
        super(position, team, movementRule);
    }

    public static Cannon of(final Position position, final Team team) {
        return new Cannon(position, team, StraightMovementRule.withBlock(REQUIRE_BLOCK_COUNT));
    }

    public static List<Cannon> defaultsOf(final Team team) {
        final int defaultRow = Team.decideRow(3, team);
        final List<Integer> defaultColumns = List.of(2, 8);

        return defaultColumns.stream()
                .map(defaultColumn -> Cannon.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Cannon.of(position, team);
    }

    @Override
    protected void validateSpecialRule(final Board board, final Position destination) {
        validateNoCannonInRoute(board, destination);
    }

    private void validateNoCannonInRoute(final Board board, final Position destination) {
        final boolean hasCannonInRoute = Route.of(position, destination).calculateWithDestination().stream()
                .filter(board::isExists)
                .anyMatch(position -> board.getPiece(position).getType().isCannon());

        if (hasCannonInRoute) {
            throw new IllegalArgumentException("포는 경로에 포가 존재할 때, 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }
}

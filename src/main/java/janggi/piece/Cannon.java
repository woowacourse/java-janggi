package janggi.piece;

import janggi.Board;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.coordinate.Route;
import janggi.piece.strategy.block.RequiredBlockCountStrategy;
import janggi.piece.strategy.move.MoveStrategy;
import janggi.piece.strategy.move.StraightMoveStrategy;

import java.util.List;

public class Cannon extends Piece {

    public static final int REQUIRE_BLOCK_COUNT = 1;

    private Cannon(final Position position,
                   final Team team,
                   final MoveStrategy moveStrategy,
                   final RequiredBlockCountStrategy blockStrategy) {
        super(position, team, moveStrategy, blockStrategy);
    }

    public static Cannon of(final Position position, final Team team) {
        return new Cannon(
                position,
                team,
                new StraightMoveStrategy(),
                new RequiredBlockCountStrategy(REQUIRE_BLOCK_COUNT));
    }

    public static List<Cannon> defaultsOf(Team team) {
        int defaultRow = Team.decideRow(3, team);
        List<Integer> defaultColumns = List.of(2, 8);

        return defaultColumns.stream()
                .map(defaultColumn -> Cannon.of(Position.of(defaultRow, defaultColumn), team))
                .toList();
    }

    @Override
    protected Piece createPiece(final Position position) {
        return Cannon.of(position, team);
    }

    @Override
    protected void validateSpecialRule(Board board, Position destination) {
        boolean excludeDestination = false;

        boolean containsCannon = Route.of(position, destination).calculate(excludeDestination).stream()
                .filter(board::isExists)
                .anyMatch(position -> board.getPiece(position).getType().isCannon());

        if (containsCannon) {
            throw new IllegalArgumentException("이동 경로에 포가 존재합니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }
}

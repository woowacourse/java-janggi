package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import janggi.domain.piece.move.AndMoveStrategy;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.strategy.NoObstacleStrategy;
import janggi.domain.piece.move.strategy.PalaceAreaStrategy;
import java.util.List;
import java.util.Set;

public class Guard extends Piece {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT),
            List.of(Direction.DOWN_LEFT), List.of(Direction.DOWN_RIGHT), List.of(Direction.UP_LEFT),
            List.of(Direction.UP_RIGHT)
    );

    private static final MoveStrategy MOVE_STRATEGY = new AndMoveStrategy(
            List.of(new PalaceAreaStrategy(), new NoObstacleStrategy())
    );

    public Guard(Dynasty dynasty) {
        super(PieceType.GUARD, dynasty, MOVE_STRATEGY);
    }

    @Override
    public Path calculatePath(Point start, Point end) {
        return Path.calculatePath(start, end, PATHS);
    }
}

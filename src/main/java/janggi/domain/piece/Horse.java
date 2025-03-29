package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.strategy.NoObstacleStrategy;
import java.util.List;
import java.util.Set;

public class Horse extends Piece {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.UP, Direction.UP_LEFT),
            List.of(Direction.UP, Direction.UP_RIGHT),

            List.of(Direction.DOWN, Direction.DOWN_LEFT),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT),

            List.of(Direction.RIGHT, Direction.UP_RIGHT),
            List.of(Direction.RIGHT, Direction.DOWN_RIGHT),

            List.of(Direction.LEFT, Direction.UP_LEFT),
            List.of(Direction.LEFT, Direction.DOWN_LEFT)
    );

    private final static MoveStrategy MOVE_STRATEGY = new NoObstacleStrategy();

    public Horse(Dynasty dynasty) {
        super(PieceType.HORSE, dynasty, MOVE_STRATEGY);
    }

    @Override
    public Path calculatePath(Point start, Point end) {
        return Path.calculatePath(start, end, PATHS);
    }
}

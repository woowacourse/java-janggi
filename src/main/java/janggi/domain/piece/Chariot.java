package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.strategy.NoObstacleStrategy;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Direction> DIRECTIONS = List.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    private static final MoveStrategy MOVE_STRATEGY = new NoObstacleStrategy();

    public Chariot(Dynasty dynasty) {
        super(PieceType.CHARIOT, dynasty, MOVE_STRATEGY);
    }

    @Override
    public Path calculatePath(Point start, Point end) {
        return Path.calculatePath(start, end, DIRECTIONS);
    }
}

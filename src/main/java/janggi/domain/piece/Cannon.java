package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import janggi.domain.piece.move.AndMoveStrategy;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.strategy.AvoidPieceOnPathStrategy;
import janggi.domain.piece.move.strategy.JumpObstacleStrategy;
import java.util.List;

public class Cannon extends Piece {

    private static final List<Direction> DIRECTIONS = List.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    private static final PieceType AVOID_PIECETYPE = PieceType.CANNON;

    private static final MoveStrategy MOVE_STRATEGY = new AndMoveStrategy(
            List.of(new JumpObstacleStrategy(),
                    new AvoidPieceOnPathStrategy(AVOID_PIECETYPE)
            ));

    public Cannon(Dynasty dynasty) {
        super(PieceType.CANNON, dynasty, MOVE_STRATEGY);
    }

    @Override
    public Path calculatePath(Point start, Point end) {
        return Path.calculatePath(start, end, DIRECTIONS);
    }
}
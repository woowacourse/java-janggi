package janggi.domain.piece;

import janggi.domain.board.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.OrMoveStrategy;
import janggi.domain.piece.move.Path;
import janggi.domain.piece.move.strategy.NoObstacleStrategy;
import janggi.domain.piece.move.strategy.PalaceAreaStrategy;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Direction> DIRECTIONS = List.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    private static final List<Direction> PALACE_DIRECTIONS = List.of(
            Direction.UP_RIGHT, Direction.UP_LEFT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT
    );

    private static final MoveStrategy MOVE_STRATEGY = new OrMoveStrategy(
            List.of(new NoObstacleStrategy((start, end) -> Path.calculatePath(start, end, DIRECTIONS)),
                    new PalaceAreaStrategy(((start, end) -> Path.calculatePath(start, end, PALACE_DIRECTIONS)))
        ));

    public Chariot(Dynasty dynasty) {
        super(PieceType.CHARIOT, dynasty, MOVE_STRATEGY);
    }
}

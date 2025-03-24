package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.moveStrategy.FixedRangeMoveStrategy;
import janggi.domain.piece.moveStrategy.MoveStrategy;
import java.util.List;
import java.util.Set;

public class Horse implements Piece {

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

    private final MoveStrategy moveStrategy = new FixedRangeMoveStrategy();

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Point start, Point end) {
        return moveStrategy.isMovable(janggiBoard, start, end, PATHS);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

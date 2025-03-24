package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.moveStrategy.FixedRangeMoveStrategy;
import janggi.domain.piece.moveStrategy.MoveStrategy;
import java.util.List;
import java.util.Set;

public class Soldier implements Piece {

    private static final Set<List<Direction>> CHU_PATHS = Set.of(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    private static final Set<List<Direction>> HAN_PATHS = Set.of(
            List.of(Direction.DOWN),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    private final MoveStrategy moveStrategy = new FixedRangeMoveStrategy();

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Point start, Point end) {
        if (dynasty == Dynasty.HAN) {
            return isMovable(janggiBoard, start, end, HAN_PATHS);
        }
        return isMovable(janggiBoard, start, end, CHU_PATHS);
    }

    private boolean isMovable(JanggiBoard janggiBoard, Point start, Point end, Set<List<Direction>> paths) {
        return moveStrategy.isMovable(janggiBoard, start, end, paths);
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

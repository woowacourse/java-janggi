package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.moveStrategy.LongRangeMoveStrategy;
import janggi.domain.piece.moveStrategy.MoveStrategy;
import java.util.List;
import java.util.Set;

public class Chariot implements Piece {

    private final Set<List<Direction>> DIRECTIONS = Set.of(
            List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    private final MoveStrategy moveStrategy = new LongRangeMoveStrategy();

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Point start, Point end) {
        return moveStrategy.isMovable(janggiBoard, start, end, DIRECTIONS);
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

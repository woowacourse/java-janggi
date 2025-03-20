package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;
import java.util.List;
import java.util.Set;

public class Knight implements Piece {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.UP, Direction.UP_LEFT_DIAGONAL),
            List.of(Direction.UP, Direction.UP_RIGHT_DIAGONAL),

            List.of(Direction.DOWN, Direction.DOWN_LEFT_DIAGONAL),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT_DIAGONAL),

            List.of(Direction.RIGHT, Direction.UP_RIGHT_DIAGONAL),
            List.of(Direction.RIGHT, Direction.DOWN_RIGHT_DIAGONAL),

            List.of(Direction.LEFT, Direction.UP_LEFT_DIAGONAL),
            List.of(Direction.LEFT, Direction.DOWN_LEFT_DIAGONAL)
    );

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        for (List<Direction> path : PATHS) {
            if (canMoveEndPointByPath(janggiBoard, start, end, path)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveEndPointByPath(JanggiBoard janggiBoard, Point start, Point end, List<Direction> path) {
        Point currPoint = start;
        for (Direction direction : path) {
            currPoint = currPoint.move(direction);
            if (janggiBoard.isExistPiece(currPoint)) {
                break;
            }
        }
        return currPoint.isSamePosition(end);
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

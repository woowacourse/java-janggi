package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;
import java.util.Set;

public class Chariot implements Piece {

    private final Set<Direction> DIRECTIONS = Set.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return DIRECTIONS.stream()
                .anyMatch(path -> canMoveEndPointByDirection(janggiBoard, start, end, path));
    }

    private boolean canMoveEndPointByDirection(JanggiBoard janggiBoard, Point start, Point end,
                                               Direction direction) {
        Point currPoint = start;
        while (canMoveUntilEndPoint(end, currPoint)) {
            currPoint = currPoint.move(direction);
            if (janggiBoard.isExistPiece(currPoint)) {
                break;
            }
        }
        return currPoint.isSamePosition(end);
    }

    private boolean canMoveUntilEndPoint(Point end, Point currPoint) {
        return !currPoint.isSamePosition(end) && currPoint.isNotOutOfBoundary();
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

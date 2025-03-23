package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import java.util.List;
import java.util.Set;

public class Horse implements Piece {

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
    public boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Position start, Position end) {
        return PATHS.stream()
                .anyMatch(path -> canMoveEndPointByPath(janggiBoard, start, end, path));
    }

    private boolean canMoveEndPointByPath(JanggiBoard janggiBoard, Position start, Position end, List<Direction> path) {
        Position currPoint = start;
        for (Direction direction : path) {
            if (!currPoint.canMove(direction)) {
                break;
            }
            currPoint = currPoint.move(direction);
            if (janggiBoard.isExistPiece(currPoint)) {
                break;
            }
        }
        return currPoint.equals(end);
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

package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;
import java.util.Optional;
import java.util.Set;

public class Cannon implements Piece {

    private static final Set<Direction> DIRECTIONS = Set.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        if (isExistCannon(janggiBoard, end)) {
            return false;
        }
        for (Direction direction : DIRECTIONS) {
            if (canMoveEndPoint(janggiBoard, start, end, direction)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveUntilEndPoint(Point end, Point currPoint) {
        return !currPoint.isSamePosition(end) && currPoint.isNotOutOfBoundary();
    }

    private boolean canMoveEndPoint(JanggiBoard janggiBoard, Point start, Point end, Direction direction) {
        boolean isJump = false;
        Point current = start;
        while (canMoveUntilEndPoint(end, current)) {
            current = current.move(direction);
            if (isExistCannon(janggiBoard, current)) {
                return false;
            }
            if (isAlreadyJumpedAndExistPiece(janggiBoard, isJump, current)) {
                break;
            }
            if (janggiBoard.isExistPiece(current)) {
                isJump = true;
            }
        }
        return isJump && current.isSamePosition(end);
    }

    private boolean isExistCannon(JanggiBoard janggiBoard, Point point) {
        Optional<BoardPiece> pointPiece = janggiBoard.findPointPiece(point);
        if (pointPiece.isPresent()) {
            BoardPiece piece = pointPiece.get();
            return piece.isEqualPiece(this);
        }
        return false;
    }

    private boolean isAlreadyJumpedAndExistPiece(JanggiBoard janggiBoard, boolean isJump, Point current) {
        return isJump && janggiBoard.isExistPiece(current);
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
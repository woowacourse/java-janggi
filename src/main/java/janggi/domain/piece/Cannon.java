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
            boolean isJump = false;
            Point currPoint = start;
            while (!currPoint.isSamePosition(end) && currPoint.isNotOutOfBoundary()) {
                currPoint = currPoint.move(direction);
                if (janggiBoard.isExistPiece(currPoint)) {
                    if (isExistCannon(janggiBoard, currPoint)) {
                        break;
                    }
                    if (isJump) {
                        break;
                    }
                    isJump = true;
                }
            }
            if (isJump && currPoint.isSamePosition(end)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExistCannon(JanggiBoard janggiBoard, Point point) {
        Optional<BoardPiece> pointPiece = janggiBoard.findPointPiece(point);
        if (pointPiece.isPresent()) {
            BoardPiece piece = pointPiece.get();
            return piece.isEqualPiece(this);
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        return this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
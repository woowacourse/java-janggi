package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;
import java.util.Optional;
import java.util.Set;

public class Cannon implements Piece {

    private static final Cannon CANNON = new Cannon();
    private static final Set<Direction> DIRECTIONS = Set.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    private Cannon() {
    }

    public static Cannon newInstance() {
        return CANNON;
    }

    private static boolean isExistCannon(JanggiBoard janggiBoard, Point point) {
        Optional<BoardPiece> pointPiece = janggiBoard.findPointPiece(point);
        if (pointPiece.isPresent()) {
            BoardPiece piece = pointPiece.get();
            return piece.isEqualPiece(CANNON);
        }
        return false;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        if (isExistCannon(janggiBoard, end)) {
            return false;
        }
        for (Direction direction : DIRECTIONS) {
            boolean isJump = false;
            Point currPoint = start;
            while (!currPoint.isSamePosition(end) && !currPoint.isOutOfBoundary()) {
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
}
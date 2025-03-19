package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;
import java.util.List;
import java.util.Set;

public class Knight implements Piece {

    private static final Knight KNIGHT = new Knight();
    private static final Set<List<Direction>> DIRECTIONS = Set.of(
            List.of(Direction.UP, Direction.UP_LEFT_DIAGONAL),
            List.of(Direction.UP, Direction.UP_RIGHT_DIAGONAL),

            List.of(Direction.DOWN, Direction.DOWN_LEFT_DIAGONAL),
            List.of(Direction.DOWN, Direction.DOWN_RIGHT_DIAGONAL),

            List.of(Direction.RIGHT, Direction.UP_RIGHT_DIAGONAL),
            List.of(Direction.RIGHT, Direction.DOWN_RIGHT_DIAGONAL),

            List.of(Direction.LEFT, Direction.UP_LEFT_DIAGONAL),
            List.of(Direction.LEFT, Direction.DOWN_LEFT_DIAGONAL)
    );

    private Knight() {
    }

    public static Knight newInstance() {
        return KNIGHT;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        for (List<Direction> directions : DIRECTIONS) {
            Point currPoint = start;
            for (Direction direction : directions) {
                currPoint = currPoint.move(direction);
                if (janggiBoard.isExistPiece(currPoint)) {
                    break;
                }
            }
            if (currPoint.isSamePosition(end)) {
                return true;
            }
        }
        return false;
    }
}

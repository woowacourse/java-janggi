package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.Point;
import java.util.Set;

public class Rook implements Piece {

    private final static Rook ROOK = new Rook();
    private static final Set<Direction> DIRECTIONS = Set.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    private Rook() {
    }

    public static Rook newInstance() {
        return ROOK;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        for (Direction direction : DIRECTIONS) {
            Point currPoint = start;
            while (!currPoint.isSamePosition(end) && !currPoint.isOutOfBoundary()) {
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

package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import java.util.Set;

public class Chariot implements Piece {

    private final Set<Direction> DIRECTIONS = Set.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Position start, Position end) {
        return DIRECTIONS.stream()
                .anyMatch(path -> canMoveEndPointByDirection(janggiBoard, start, end, path));
    }

    private boolean canMoveEndPointByDirection(JanggiBoard janggiBoard, Position start, Position end,
                                               Direction direction) {
        Position currPoint = start;
        while (!currPoint.equals(end)) {
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

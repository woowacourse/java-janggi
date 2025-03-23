package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import java.util.List;
import java.util.Set;

public class Soldier implements Piece {

    private static final Set<List<Direction>> CHU_PATHS = Set.of(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );
    private static final Set<List<Direction>> HAN_PATHS = Set.of(
            List.of(Direction.DOWN),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Position start, Position end) {
        if (dynasty == Dynasty.HAN) {
            return isMovable(janggiBoard, start, end, HAN_PATHS);
        }
        return isMovable(janggiBoard, start, end, CHU_PATHS);
    }

    public boolean isMovable(JanggiBoard janggiBoard, Position start, Position end, Set<List<Direction>> paths) {
        return paths.stream()
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

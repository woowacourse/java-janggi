package janggi.domain.piece.moveStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.List;
import java.util.Set;

public class FixedRangeMoveStrategy implements MoveStrategy {

    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end, Set<List<Direction>> paths) {
        return paths.stream()
                .anyMatch(path -> canMoveEndPointByPath(janggiBoard, start, end, path));
    }

    private boolean canMoveEndPointByPath(JanggiBoard janggiBoard, Point current, Point end, List<Direction> path) {
        for (Direction direction : path) {
            if (!direction.canMoveFrom(current)) {
                break;
            }
            current = direction.moveFrom(current);
            if (janggiBoard.isExistPiece(current)) {
                break;
            }
        }
        return current.equals(end);
    }
}

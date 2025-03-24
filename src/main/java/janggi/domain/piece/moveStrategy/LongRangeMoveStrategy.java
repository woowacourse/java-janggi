package janggi.domain.piece.moveStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.List;
import java.util.Set;

public class LongRangeMoveStrategy implements MoveStrategy {

    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end, Set<List<Direction>> directions) {
        return directions.stream()
                .anyMatch(path -> canMoveEndPointByDirection(janggiBoard, start, end, path.getFirst()));
    }

    private boolean canMoveEndPointByDirection(JanggiBoard janggiBoard, final Point start, Point end,
                                               Direction direction) {
        Point current = start;
        while (!current.equals(end) && direction.canMoveFrom(current)) {
            current = direction.moveFrom(current);
            if (janggiBoard.isExistPiece(current)) {
                break;
            }
        }
        return current.equals(end);
    }
}

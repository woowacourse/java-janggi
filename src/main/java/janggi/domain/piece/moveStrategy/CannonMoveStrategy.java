package janggi.domain.piece.moveStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.List;
import java.util.Set;


public class CannonMoveStrategy implements MoveStrategy {

    public boolean isMovable(JanggiBoard janggiBoard, Point start, Point end, Set<List<Direction>> directions) {
        return directions.stream()
                .anyMatch(path -> canMoveEndPoint(janggiBoard, start, end, path.getFirst()));
    }

    private boolean canMoveEndPoint(JanggiBoard janggiBoard, Point current, Point end, Direction direction) {
        boolean isJump = false;
        while (isNotEnd(current, end) && direction.canMoveFrom(current)) {
            current = direction.moveFrom(current);
            if (canNotJumpButPieceExit(janggiBoard, current, isJump)) {
                break;
            }
            if (canJump(janggiBoard, current, isJump)) {
                isJump = true;
            }
        }
        return isJump && current.equals(end) && !janggiBoard.isExistCannon(end);
    }

    private boolean isNotEnd(Point current, Point end) {
        return !current.equals(end);
    }

    private boolean canNotJumpButPieceExit(JanggiBoard janggiBoard, Point current, boolean isJump) {
        return janggiBoard.isExistCannon(current) || isAlreadyJumpedAndExistPiece(janggiBoard, isJump, current);
    }

    private boolean canJump(JanggiBoard janggiBoard, Point current, boolean isJump) {
        return janggiBoard.isExistPiece(current) && !isJump;
    }

    private boolean isAlreadyJumpedAndExistPiece(JanggiBoard janggiBoard, boolean isJump, Point current) {
        return isJump && janggiBoard.isExistPiece(current);
    }

}

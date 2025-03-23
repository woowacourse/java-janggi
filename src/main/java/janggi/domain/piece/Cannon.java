package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import java.util.Optional;
import java.util.Set;

public class Cannon implements Piece {

    private static final Set<Direction> DIRECTIONS = Set.of(
            Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT
    );

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Dynasty dynasty, Position start, Position end) {
        if (isExistCannon(janggiBoard, end)) {
            return false;
        }
        return DIRECTIONS.stream()
                .anyMatch(path -> canMoveEndPoint(janggiBoard, start, end, path));
    }

    private boolean canMoveEndPoint(JanggiBoard janggiBoard, Position start, Position end, Direction direction) {
        boolean isJump = false;
        Position current = start;
        while (!current.equals(end)) {
            if (current.canMove(direction)) {
                break;
            }
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
        return isJump && current.equals(end);
    }

    private boolean isExistCannon(JanggiBoard janggiBoard, Position point) {
        Optional<BoardPiece> pointPiece = janggiBoard.findPointPiece(point);
        if (pointPiece.isPresent()) {
            BoardPiece piece = pointPiece.get();
            return piece.isEqualPiece(this);
        }
        return false;
    }

    private boolean isAlreadyJumpedAndExistPiece(JanggiBoard janggiBoard, boolean isJump, Position current) {
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
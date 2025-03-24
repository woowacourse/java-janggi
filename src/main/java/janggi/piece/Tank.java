package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;

public class Tank extends Piece {

    public Tank(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(final Position start, final Position end, final Map<Position, Piece> board) {
        return isEmptyOnPath(board, findPath(start, end)) && isValidMovingRule(start, end);
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    private boolean isEmptyOnPath(final Map<Position, Piece> board, final List<Position> path) {
        return path.stream()
                .noneMatch(board::containsKey);
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        return start.isHorizontalMove(end) || start.isVerticalMove(end);
    }

    private List<Position> findPath(final Position start, final Position end) {
        if (start.isHorizontalMove(end)) {
            return start.horizontalPath(end);
        }
        return start.verticalPath(end);
    }
}

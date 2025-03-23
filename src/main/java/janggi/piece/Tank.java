package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Tank extends Piece {

    public Tank(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(Position start, Position end, Map<Position, Piece> board) {
        return isEmptyOnPath(board, findPath(start, end)) && isValidMovingRule(start, end);
    }

    private boolean isEmptyOnPath(Map<Position, Piece> board, List<Position> path) {
        return path.stream()
                .noneMatch(board::containsKey);
    }

    private List<Position> findPath(final Position start, final Position end) {
        if (start.isHorizontalMove(end)) {
            return findHorizontalPath(start, end);
        }
        return findVerticalPath(start, end);
    }

    private List<Position> findHorizontalPath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int movingCount = Math.abs(differenceX);
        if (differenceX > 0) {
            return IntStream.range(1, movingCount)
                    .mapToObj(start::right)
                    .toList();
        }
        return IntStream.range(1, movingCount)
                .mapToObj(start::left)
                .toList();
    }

    private List<Position> findVerticalPath(final Position start, final Position end) {
        int differenceY = end.y() - start.y();
        int differenceYAmount = Math.abs(differenceY);
        if (differenceY > 0) {
            return IntStream.range(1, differenceYAmount)
                    .mapToObj(start::up)
                    .toList();
        }
        return IntStream.range(1, differenceYAmount)
                .mapToObj(start::down)
                .toList();
    }

    private boolean isValidMovingRule(final Position start, final Position end) {
        return start.isHorizontalMove(end) || start.isVerticalMove(end);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        if (isValidMovingRule(start, end)) {
            return findPath(start, end);
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

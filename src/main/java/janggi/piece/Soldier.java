package janggi.piece;

import janggi.position.Direction;
import janggi.position.Position;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Color color) {
        super(color);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        validateMovingRule(start, end);
        validateDirection(start, end);
        return List.of();
    }

    private void validateMovingRule(final Position start, final Position end) {
        int absDifferenceX = Math.abs(end.x() - start.x());
        int absDifferenceY = Math.abs(end.y() - start.y());
        if ((absDifferenceX == 1 && absDifferenceY == 0)
                || (absDifferenceX == 0 && absDifferenceY == 1)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }

    private void validateDirection(final Position start, final Position end) {
        final Direction direction = calculateDirection(start, end);
        if (color.isReverseFrontVerticalDirection(direction)) {
            throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
        }
    }

    private Direction calculateDirection(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        if (differenceX < 0) {
            return Direction.RIGHT;
        }
        if (differenceX > 0) {
            return Direction.LEFT;
        }
        if (differenceY < 0) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }
}

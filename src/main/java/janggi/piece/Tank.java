package janggi.piece;

import janggi.position.Direction;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Tank extends Piece {

    public Tank(final Color color) {
        super(color);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        validateMovingRule(differenceX, differenceY);
        return findPath(start, end);
    }

    private List<Position> findPath(final Position start, final Position end) {
        Direction direction = calculateDirection(start, end);
        List<Position> result = new ArrayList<>();
        for (Position path = start.move(direction); !path.equals(end); path = path.move(direction)) {
            result.add(path);
        }
        return result;
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

    private void validateMovingRule(final int differenceX, final int differenceY) {
        if ((differenceX == 0 && differenceY != 0) || (differenceX != 0 && differenceY == 0)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

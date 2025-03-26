package janggi.piece;

import janggi.position.Direction;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(final Color color) {
        super(color);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        validateMovingRule(start, end);
        return findPath(start, end);
    }

    private List<Position> findPath(final Position start, final Position end) {
        Direction direction = start.calculateDirection(end);
        List<Position> result = new ArrayList<>();
        for (Position path = start.move(direction); !path.equals(end); path = path.move(direction)) {
            result.add(path);
        }
        return result;
    }

    private void validateMovingRule(final Position start, final Position end) {
        int differenceX = start.calculateDifferenceX(end);
        int differenceY = start.calculateDifferenceY(end);
        // NOTE: 가로 또는 세로 방향으로만 이동하는지 검증한다.
        // 가로만 이동하는 경우,                           세로만 이동하는 경우.
        if ((differenceX == 0 && differenceY != 0) || (differenceX != 0 && differenceY == 0)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

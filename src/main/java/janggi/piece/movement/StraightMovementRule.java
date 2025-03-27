package janggi.piece.movement;

import janggi.position.Direction;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public class StraightMovementRule implements MovementRule {

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        final Direction direction = start.calculateDirection(end);
        final List<Position> result = new ArrayList<>();
        for (Position path = start.move(direction); !path.equals(end); path = path.move(direction)) {
            result.add(path);
        }
        return result;
    }

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        final int differenceX = start.calculateDifferenceX(end);
        final int differenceY = start.calculateDifferenceY(end);
        // NOTE: 가로 또는 세로 방향으로만 이동하는지 검증한다.
        // 가로만 이동하는 경우,                           세로만 이동하는 경우.
        if ((differenceX == 0 && differenceY != 0) || (differenceX != 0 && differenceY == 0)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}

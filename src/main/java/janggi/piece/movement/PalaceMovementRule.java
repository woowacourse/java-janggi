package janggi.piece.movement;

import janggi.position.Position;
import java.util.List;

public class PalaceMovementRule implements MovementRule {

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        return List.of();
    }

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        final int absDifferenceX = start.calculateAbsoluteDifferenceX(end);
        final int absDifferenceY = start.calculateAbsoluteDifferenceY(end);
        if ((absDifferenceX == 1 && absDifferenceY == 0)
                || (absDifferenceX == 0 && absDifferenceY == 1)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}
